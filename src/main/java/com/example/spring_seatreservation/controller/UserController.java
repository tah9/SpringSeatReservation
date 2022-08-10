package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.MyTask;
import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.Bean.Restful;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.Other.SignedNumber;
import com.example.spring_seatreservation.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @PostMapping("/addReservation")
    public Map<String, Object> addReservation(@RequestBody Map<String, Object> map) {
        Long startTime = new Long(map.get("startTime").toString());
        userMapper.addReservation(map);
        int rid = Integer.parseInt(map.get("rid").toString());

        //超过30分钟将设置预约状态为违约未签到
        MyTask myTask = new MyTask(map.get("uid").toString(),
                startTime + 30 * 60 * 1000L, () -> {
            userMapper.updateReservation(Restful.UNSIGNED, rid);
            System.out.println("定时任务执行");
        });
        dynamicTask.add(myTask);
        return new R().ok().getMap();
    }


    @PostMapping("/toSigned")
    public Map<String, Object> toSigned(@RequestBody Map<String, Object> map) {
        long number = new Long(map.get("number").toString());
        Map<String, Object> reservation = userMapper.getReservationByRid(map.get("rid"));

        HashMap<String, Object> result = new R().ok().getMap();
        Object state = reservation.get("state");
        if (state.equals(Restful.WAIT_SIGNED)) {
            result.put("verify", number == SignedNumber.getSignedNumber(reservation));
            if (number == SignedNumber.getSignedNumber(reservation)) {
                userMapper.updateReservation(Restful.SIGNED, reservation.get("rid"));
            }
        } else if (state.equals(Restful.LEAVE)) {
            result.put("verify", number == SignedNumber.getLeaveSignedNumber(reservation));
            if (number == SignedNumber.getLeaveSignedNumber(reservation)) {
                userMapper.updateReservation(Restful.SIGNED, reservation.get("rid"));
            }
        }
        return result;
    }
}
