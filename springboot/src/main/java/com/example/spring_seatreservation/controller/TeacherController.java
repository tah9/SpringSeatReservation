package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.TeacherMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    TeacherMapper teacherMapper;


    @GetMapping("/getReservation")
    public Map<String, Object> getReservation() {
        return new R().ok().add("rows", teacherMapper.getReservation()).builder();
    }

    @GetMapping("/getReservationNeedSub")
    public Map<String, Object> getReservationNeedSub() {
        return new R().ok().add("rows", teacherMapper.getReservationNeedSub()).builder();
    }

    @PostMapping("/subScore")
    public Map<String, Object> subScore(@RequestBody Map<String, Object> map) {
        teacherMapper.subScore(map);
        teacherMapper.subReservationScore(map);
        return new R().ok().builder();
    }

}
