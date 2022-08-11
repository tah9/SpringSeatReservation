package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.MyTask;
import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.Bean.ReservationCode;
import com.example.spring_seatreservation.Bean.SeatCode;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.Other.SignedNumber;
import com.example.spring_seatreservation.mapper.PublicMapper;
import com.example.spring_seatreservation.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

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

        List<Map<String, Object>> curReservation = userMapper.getCurReservation(map.get("uid"));
        //当前用户已有预约
        if (curReservation.size() > 0) {
            return new R().bad().builder();
        }
        long startTime = ((long) map.get("startTime"));
        long endTime = ((long) map.get("endTime"));

        String finishTaskName = ReservationCode.FINISH
                + "-" + map.get("sid");
        String unSignedTaskName = ReservationCode.UNSIGNED
                + "-" + map.get("sid");
        //插入预约记录
        userMapper.addReservation(map);
        userMapper.updateSeat(SeatCode.BE_RESERVATION, map.get("sid"));
        int rid = Integer.parseInt(map.get("rid").toString());

        //预约开始超过30分钟将设置预约状态为违约未签到，
        //并释放座椅
        dynamicTask.add(new MyTask(unSignedTaskName,
                startTime + 30 * 60 * 1000L, () -> {
            dynamicTask.stop(finishTaskName);
            userMapper.updateReservation(ReservationCode.UNSIGNED, rid);
            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
        }));

        //预约结束时自动恢复座位状态
        dynamicTask.add(new MyTask(finishTaskName, endTime, () -> {
            userMapper.updateReservation(ReservationCode.FINISH, rid);
            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
        }));

        return new R().ok().builder();
    }


    /**
     * 通过rid签到
     * 预约开始的时间前后半小时内有效(暂离1小时内有效)
     *
     * @param map
     * @return
     */
    @PostMapping("/toSigned")
    public Map<String, Object> toSigned(@RequestBody Map<String, Object> map) {
        long number = new Long(map.get("number").toString());
        //获取预约记录
        Map<String, Object> reservation = userMapper.getReservationByRid(map.get("rid"));

        long currentTimeMillis = System.currentTimeMillis();
        Object state = reservation.get("state");
        //预约签到
        if (state.equals(ReservationCode.TIME_BEGAN)) {
            if (currentTimeMillis > ((long) reservation.get("startTime")) + 30 * 60 * 1000L
                    || currentTimeMillis < ((long) reservation.get("startTime")) - 30 * 60 * 1000L) {
                //不在前后半小时内
                return new R().bad().builder();
            }
            boolean flag = number == SignedNumber.getSignedNumber(reservation);
            if (flag) {
                userMapper.updateReservation(ReservationCode.SIGNED_BE_USE, reservation.get("rid"));
                userMapper.updateSeat(SeatCode.BE_USE, reservation.get("sid"));
                //预约签到成功，关闭签到倒计时任务
                dynamicTask.stop(ReservationCode.UNSIGNED + "-" + reservation.get("sid").toString());
            }
            return new R().ok().add("verify", flag).builder();

        }
        //暂离签到
        else if (state.equals(ReservationCode.LEAVE)) {
            if (currentTimeMillis > ((long) reservation.get("leaveTime")) + 60 * 60 * 1000L) {
                //暂离超过1小时
                return new R().bad().builder();
            }
            boolean flag = number == SignedNumber.getLeaveSignedNumber(reservation);
            if (flag) {
                userMapper.updateReservation(ReservationCode.SIGNED_BE_USE, reservation.get("rid"));
                userMapper.updateSeat(SeatCode.BE_USE, reservation.get("sid"));
                //暂离回来签到成功，关闭签到倒计时任务
                dynamicTask.stop(ReservationCode.LEAVE_UNSIGNED + "-" + reservation.get("sid").toString());
            }
            return new R().ok().add("verify", flag).builder();
        } else {
            return new R().bad().builder();
        }
    }


    /**
     * 暂离,rid,sid
     *
     * @param map
     * @return
     */
    @PostMapping("/toLeave")
    public Map<String, Object> toLeave(@RequestBody Map<String, Object> map) {
        long leaveTime = System.currentTimeMillis();
        //更新数据库中预约状态和座位状态
        userMapper.leaveReservation(leaveTime, map.get("rid"));
        userMapper.leaveSeat(map.get("sid"));
        //超过60分钟未签到将设置状态为暂离超时未签到，同时释放座位
        dynamicTask.add(new MyTask(ReservationCode.LEAVE_UNSIGNED + "-" + map.get("sid"),
                leaveTime + 60 * 60 * 1000L, () -> {
            dynamicTask.stop(ReservationCode.FINISH + "-" + map.get("sid").toString());
            userMapper.updateReservation(ReservationCode.LEAVE_UNSIGNED, map.get("rid"));
            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
        }));
        return new R().builder();
    }


//    /**
//     * 获取当前其他用户的预约，得到自己可以预约的时间范围
//     * curTime,sid,
//     * @param map
//     * @return
//     */
//    @PostMapping("/getTimeRange")
//    public Map<String, Object> getTimeRange(@RequestBody Map<String, Object> map) {
//        List<Map<String, Object>> list = userMapper.getTimeRange(map);
//        return new R().ok().add("rows", ).builder();
//    }


    @PostMapping("/getReservation")
    public Map<String, Object> getReservationNeedSub(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", userMapper.getReservation(map)).builder();
    }

    @PostMapping("/getReservationByUid")
    public Map<String, Object> getReservationByUid(@RequestBody Map<String, Object> map) {
        List<Map<String, Object>> list = userMapper.getReservationByUid(map.get("uid"));
        return new R().add("rows", list).builder();
    }

    @PostMapping("/getScore")
    public Map<String, Object> getScore(@RequestBody Map<String, Object> map) {
        return new R().add("score", userMapper.getScore(map.get("uid"))).builder();
    }
//
//    @PostMapping("/getReservationToSigned")
//    public Map<String, Object> getReservationToSigned(@RequestBody Map<String, Object> map) {
//        ListMap<String, Object> result = userMapper.getReservationToSigned(map.get("uid"));
//        return new R().add("score", ).builder();
//    }


}
