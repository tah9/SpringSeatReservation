package com.example.spring_seatreservation.controller;


import com.example.spring_seatreservation.Bean.MyUser;
import com.example.spring_seatreservation.Other.UpPic;
import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.PublicMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Resource
    PublicMapper publicMapper;


    @GetMapping("/getAnnounce")
    public Map<String, Object> getAnnounce() {
        return new R().ok().add("rows", publicMapper.getAnnounce()).getMap();
    }

    @GetMapping("/getStatistic")
    public Map<String, Object> getStatistic() {
        return new R().ok().add("rows",publicMapper.getStatistic()).getMap();
    }


    @GetMapping("/getSport")
    public Map<String, Object> getSport() {
        return new R().ok().add("rows", publicMapper.getSport()).getMap();
    }

    @GetMapping("/getClassList")
    public Map<String, Object> getClassList() {
        return new R().ok().add("rows", publicMapper.getClassList()).getMap();
    }


    @PostMapping("/upPic")
    @ResponseBody
    public Map<String, Object> getUrl(HttpServletRequest request) throws Exception {
        String s = UpPic.upPics(request);
        System.out.println(s);
        return new R().ok().add("urls", s).getMap();
    }


    @PostMapping("/upPwd")
    public Map<String, Object> updatePwd(@RequestBody Map<String, Object> map) {
        String username = map.get("username").toString();
        if (publicMapper.getUserByUserName(username).getPassword().equals(map.get("opassword"))) {
            publicMapper.updatePwd(map.get("npassword").toString()
                    , username);
            return new R().ok().getMap();
        } else {
            return new R().bad().getMap();
        }
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody MyUser user) {
        System.out.println(user);
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
            System.out.println(user);
            MyUser resultUser = publicMapper.getUserByUserName(user.getUsername());
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
