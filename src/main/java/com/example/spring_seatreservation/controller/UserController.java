package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/updateMess")
    public Map<String, Object> updateMess(@RequestBody Map<String, Object> map) {
        userMapper.updateMess(map);
        return new R().ok().getMap();
    }

    @PostMapping("/getMess")
    public Map<String, Object> getMess(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", userMapper.getMess(map)).getMap();
    }

    @PostMapping("/getTask")
    public Map<String, Object> getTask(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", userMapper.getTask(map)).getMap();
    }

    @PostMapping("/getSport")
    public Map<String, Object> getSport(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", userMapper.getSport(map)).add("beJoin", userMapper.getScore(map)).getMap();
    }

    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody Map<String, Object> map) {
        userMapper.join(map);
        return new R().ok().getMap();
    }

    @PostMapping("/getScoreByUid")
    public Map<String, Object> getScoreByUid(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", userMapper.getScoreByUid(map)).getMap();
    }


}
