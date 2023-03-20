package com.example.spring_seatreservation.controller;


import com.example.spring_seatreservation.Bean.*;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.Other.SignedNumber;
import com.example.spring_seatreservation.mapper.PublicMapper;
import com.example.spring_seatreservation.mapper.UserMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/public")
public class PublicController {
    @Resource
    PublicMapper publicMapper;
    @Resource
    UserMapper userMapper;


    private final DynamicTaskService dynamicTask;


    public PublicController(DynamicTaskService dynamicTask) {
        this.dynamicTask = dynamicTask;
    }

    {
        //启动项目,重启预约线程
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //所有等待或使用状态的预约都需要检查 状态0/1/3的数据
                List<Map<String, Object>> list = publicMapper.getNeedCheckReservation();

                for (Map<String, Object> map : list) {
                    int state = (int) map.get("state");
                    long startTime = new Long(map.get("startTime").toString());

                    //预约结束时自动恢复座位状态
                    dynamicTask.add(new MyTask(ReservationCode.FINISH + "-" + map.get("sid"), (long) map.get("endTime"), () -> {
                        userMapper.updateReservation(ReservationCode.FINISH, map.get("rid"));
                        userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
                    }));
                    //使用中的
                    if (state == ReservationCode.SIGNED_BE_USE) {

                    }
                    //预约中的
                    else if (state == ReservationCode.TIME_BEGAN) {
                        //超时设置违约状态并释放座椅
                        dynamicTask.add(new MyTask(ReservationCode.UNSIGNED + "-" + map.get("sid"),
                                startTime + 30 * 60 * 1000L, () -> {
                            dynamicTask.stop(ReservationCode.FINISH + "-" + map.get("sid"));
                            publicMapper.updateReservation(ReservationCode.UNSIGNED, map.get("rid"));
                            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
                        }));
                    }
                    //暂离的
                    else if (state == ReservationCode.LEAVE) {
                        dynamicTask.add(new MyTask(ReservationCode.LEAVE_UNSIGNED + "-" + map.get("sid"),
                                ((long) map.get("leaveTime")) + 60 * 60 * 1000L, () -> {
                            dynamicTask.stop(ReservationCode.FINISH + "-" + map.get("sid"));
                            publicMapper.updateReservation(ReservationCode.LEAVE_UNSIGNED, map.get("rid"));
                            userMapper.updateSeat(SeatCode.CAN_USE, map.get("sid"));
                        }));
                    }
                }
            }
        };
        timer.schedule(timerTask, 5000);
    }

    /**
     * 通过座位id获取签到码
     *
     * @param map
     * @return
     */
    @PostMapping("/getSignedNumber")
    public Map<String, Object> getSignedNumber(@RequestBody Map<String, Object> map) {
        Map<String, Object> reservation = publicMapper.getReservationBySid(map.get("sid"));

        HashMap<String, Object> result = new R().ok().builder();
        Object state = reservation.get("state");
        if (state.equals(ReservationCode.TIME_BEGAN)) {
            result.put("number", SignedNumber.getSignedNumber(reservation));
        } else if (state.equals(ReservationCode.LEAVE)) {
            result.put("number", SignedNumber.getLeaveSignedNumber(reservation));
        }
        return result;
    }


    /**
     * 查看已开启但还未执行的动态任务
     *
     * @return
     */
    @GetMapping
    public List<String> getStartingDynamicTask() {
        return dynamicTask.getTaskList();
    }



    /**
     * 根据名称 停止一个动态任务
     *
     * @param name
     * @return
     */
    @DeleteMapping("/{name}")
    public String stopDynamicTask(@PathVariable("name") String name) {
        // 将这个添加到动态定时任务中去
        if (!dynamicTask.stop(name)) {
            return "停止失败,任务已在进行中.";
        }
        return "任务已停止";
    }


    @GetMapping("/getArea")
    public Map<String, Object> getAnnounce() {
        return new R().ok().add("rows", publicMapper.getArea()).builder();
    }

    @PostMapping("/getAreaSeats")
    public Map<String, Object> getAreaSeats(@RequestBody Map<String, Object> map) {
        List<Map<String, Object>> areaSeats = publicMapper.getAreaSeats(map);
        for (Map<String, Object> areaSeat : areaSeats) {
            areaSeat.put("show", false);
        }
        return new R().ok().add("rows", areaSeats).builder();

    }


    @PostMapping("/upPwd")
    public Map<String, Object> updatePwd(@RequestBody Map<String, Object> map) {
        Long number = new Long(map.get("number").toString());
        if (publicMapper.getUserByNumber(number).getPassword().equals(map.get("opassword"))) {
            publicMapper.updatePwd(map.get("npassword").toString()
                    , number);
            return new R().ok().builder();
        } else {
            return new R().bad().builder();
        }
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody MyUser user) {
        try {
            publicMapper.insertUser(user);
            return new R().ok().builder();
        } catch (Exception e) {
            return new R().bad().builder();
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MyUser user) {
        try {
            MyUser resultUser = publicMapper.getUserByNumber(user.getNumber());
            System.out.println(resultUser);
            if (resultUser.getPassword().equals(user.getPassword())) {
                return new R().ok().add("user", resultUser).builder();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new R().bad().builder();
        }
    }
}
