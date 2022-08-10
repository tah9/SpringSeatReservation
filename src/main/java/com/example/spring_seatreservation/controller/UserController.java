package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.MyTask;
import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.Bean.ReservationCode;
import com.example.spring_seatreservation.Bean.SeatCode;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.Other.SignedNumber;
import com.example.spring_seatreservation.mapper.PublicMapper;
import com.example.spring_seatreservation.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    private final DynamicTaskService dynamicTask;


    public UserController(DynamicTaskService dynamicTask) {
        this.dynamicTask = dynamicTask;
    }

    @Resource
    UserMapper userMapper;
    @Resource
    PublicMapper publicMapper;


    /**
     * startTime,endTime,sid,rid,uid
     *
     * @param map
     */
    @PostMapping("/addReservation")
    public Map<String, Object> addReservation(@RequestBody Map<String, Object> map) {
        long startTime = ((long) map.get("startTime"));
        long endTime = ((long) map.get("endTime"));
        userMapper.addReservation(map);
        userMapper.updateSeat(SeatCode.BE_RESERVATION, map.get("sid"));
        int rid = Integer.parseInt(map.get("rid").toString());
        String finishTaskName = ReservationCode.FINISH
                + map.get("sid").toString();
        //结束时自动恢复座位状态
        dynamicTask.add(new MyTask(finishTaskName, endTime, () -> {
            userMapper.updateReservation(ReservationCode.FINISH, rid);
            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
        }));
        String unSignedTaskName = ReservationCode.UNSIGNED
                + map.get("sid").toString();
        //超过30分钟将设置预约状态为违约未签到
        dynamicTask.add(new MyTask(unSignedTaskName,
                startTime + 30 * 60 * 1000L, () -> {
            dynamicTask.stop(finishTaskName);
            userMapper.updateReservation(ReservationCode.UNSIGNED, rid);
            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
        }));
        return new R().ok().builder();
    }


    /**
     * 通过rid签到
     *
     * @param map
     * @return
     */
    @PostMapping("/toSigned")
    public Map<String, Object> toSigned(@RequestBody Map<String, Object> map) {
        long number = new Long(map.get("number").toString());
        //获取预约
        Map<String, Object> reservation = userMapper.getReservationByRid(map.get("rid"));

        HashMap<String, Object> result = new R().ok().builder();
        Object state = reservation.get("state");
        if (state.equals(ReservationCode.WAIT_SIGNED)) {
            result.put("verify", number == SignedNumber.getSignedNumber(reservation));
            if (number == SignedNumber.getSignedNumber(reservation)) {
                userMapper.updateReservation(ReservationCode.SIGNED_BE_USE, reservation.get("rid"));
                userMapper.updateSeat(SeatCode.BE_USE, reservation.get("sid"));
                //预约签到成功，关闭签到倒计时任务
                dynamicTask.stop(ReservationCode.UNSIGNED + reservation.get("sid").toString());
            }
        } else if (state.equals(ReservationCode.LEAVE)) {
            result.put("verify", number == SignedNumber.getLeaveSignedNumber(reservation));
            if (number == SignedNumber.getLeaveSignedNumber(reservation)) {
                userMapper.updateReservation(ReservationCode.SIGNED_BE_USE, reservation.get("rid"));
                userMapper.updateSeat(SeatCode.BE_USE, reservation.get("sid"));
                //暂离回来签到成功，关闭签到倒计时任务
                dynamicTask.stop(ReservationCode.LEAVE_UNSIGNED + reservation.get("sid").toString());
            }
        }
        return result;
    }


    @PostMapping("/getReservationByUid")
    public  Map<String, Object> getReservationByUid(@RequestBody Map<String, Object> map) {
        List<Map<String, Object>> list = userMapper.getReservationByUid(map.get("uid"));
        return new R().add("rows", list).builder();
    }
}
