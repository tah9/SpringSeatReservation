package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.AdminMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminMapper adminMapper;

    @PostMapping("/updateSport")
    public Map<String, Object> updateSport(@RequestBody Map<String, Object> map) {
        adminMapper.updateSport(map);
        return new R().ok().builder();
    }

    @PostMapping("/addAnnounce")
    public Map<String, Object> addAnnounce(@RequestBody Map<String, Object> map) {
        map.put("datetime", System.currentTimeMillis());
        adminMapper.addAnnounce(map);
        return new R().ok().builder();
    }

    @PostMapping("/addSeat")
    public Map<String, Object> addSeat(@RequestBody Map<String, Object> map) {
        adminMapper.addSeat(map);
        return new R().ok().builder();
    }

    @PostMapping("/deleteSeat")
    public Map<String, Object> deleteSeat(@RequestBody Map<String, Object> map) {
        adminMapper.deleteSeat(map);
        return new R().ok().builder();
    }
}