package com.example.spring_seatreservation.controller;


import com.example.spring_seatreservation.Bean.MyUser;
import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.Bean.MyTask;
import com.example.spring_seatreservation.Bean.Restful;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.Other.SignedNumber;
import com.example.spring_seatreservation.mapper.PublicMapper;
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


    private final DynamicTaskService dynamicTask;


    public PublicController(DynamicTaskService dynamicTask) {
        this.dynamicTask = dynamicTask;
    }

    {
        //启动项目，检查数据库不合格数据
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //查找待签到和暂离预约
                List<Map<String, Object>> list = publicMapper.getWaitReservation();
                for (Map<String, Object> map : list) {
                    long startTime = new Long(map.get("startTime").toString());
                    long currentTimeMillis = System.currentTimeMillis();
                    //预约30分钟未签到
                    if (currentTimeMillis - startTime > 30 * 60 * 1000L) {
                        publicMapper.updateReservation(Restful.UNSIGNED, map.get("rid"));
                    }
                    //暂离60分钟未签到
                    else if (currentTimeMillis - new Long(map.get("leaveTime").toString()) > 60 * 60 * 1000L) {
                        publicMapper.updateReservation(Restful.LEAVE_UNSIGNED, map.get("rid"));
                    }
                }
            }
        };
        timer.schedule(timerTask, 5000);
    }

    @PostMapping("/getSignedNumber")
    public Map<String, Object> getSignedNumber(@RequestBody Map<String, Object> map) {
        Map<String, Object> reservation = publicMapper.getReservationByRid(map.get("rid"));
        HashMap<String, Object> result = new R().ok().getMap();
        Object state = reservation.get("state");
        if (state.equals(Restful.WAIT_SIGNED)) {
            result.put("number", SignedNumber.getSignedNumber(reservation));
        } else if (state.equals(Restful.LEAVE)) {
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
    public List<String> getStartingDynamicTask(@RequestBody MyTask myTask) {
        return dynamicTask.getTaskList();
    }


    /**
     * 开启一个动态任务
     *
     * @param myTask
     * @return
     */
    @PostMapping("/dynamic")
    public String startDynamicTask(@RequestBody MyTask myTask) {
        // 将这个添加到动态定时任务中去
        myTask.setRunnable(() -> {
            System.out.println("任务执行");
            System.out.println(publicMapper.getArea().size());
            System.out.println("任务结束");
        });
        dynamicTask.add(myTask);
        return "动态任务:" + myTask.getName() + " 已开启";
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
        return new R().ok().add("rows", publicMapper.getArea()).getMap();
    }

    @PostMapping("/getAreaSeats")
    public Map<String, Object> getAreaSeats(@RequestBody Map<String, Object> map) {
        List<Map<String, Object>> areaSeats = publicMapper.getAreaSeats(map);
        for (Map<String, Object> areaSeat : areaSeats) {
            areaSeat.put("show", false);
        }
        return new R().ok().add("rows", areaSeats).getMap();

    }


    @PostMapping("/upPwd")
    public Map<String, Object> updatePwd(@RequestBody Map<String, Object> map) {
        Long number = new Long(map.get("number").toString());
        if (publicMapper.getUserByNumber(number).getPassword().equals(map.get("opassword"))) {
            publicMapper.updatePwd(map.get("npassword").toString()
                    , number);
            return new R().ok().getMap();
        } else {
            return new R().bad().getMap();
        }
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody MyUser user) {
        try {
            publicMapper.insertUser(user);
            return new R().ok().getMap();
        } catch (Exception e) {
            return new R().bad().getMap();
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody MyUser user) {
        try {
            MyUser resultUser = publicMapper.getUserByNumber(user.getNumber());
            System.out.println(resultUser);
            if (resultUser.getPassword().equals(user.getPassword())) {
                return new R().ok().add("user", resultUser).getMap();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new R().bad().getMap();
        }
    }
}
