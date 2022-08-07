package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.TeacherMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    TeacherMapper teacherMapper;

    @PostMapping("/getTask")
    public Map<String, Object> getTask() {
        return new R().ok().add("rows", teacherMapper.getTask()).getMap();
    }

    @PostMapping("/insertTask")
    public Map<String, Object> insertTask(@RequestBody Map<String, Object> map) {
        map.put("datetime", System.currentTimeMillis());
        teacherMapper.insertTask(map);
        return new R().ok().getMap();
    }


    @PostMapping("/getScoreByTid")
    public Map<String, Object> getScoreByTid(@RequestBody Map<String, Object> map) {
        return new R().ok().add("rows", teacherMapper.getScoreByTid(map)).getMap();
    }


    @PostMapping("/updateScore")
    public Map<String, Object> updateScore(@RequestBody Map<String, Object> map) {
        teacherMapper.updateScore(map);
        return new R().ok().getMap();
    }



}
