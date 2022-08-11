package com.example.spring_seatreservation.controller;

import com.example.spring_seatreservation.Bean.R;
import com.example.spring_seatreservation.mapper.ForumMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Resource
    ForumMapper forumMapper;

    @GetMapping("/getArticle")
    public Map<String, Object> getArticle() {
        return new R().add("rows", forumMapper.getArticle()).builder();
    }

    @PostMapping("/insertArticle")
    public Map<String, Object> insertArticle(@RequestBody Map<String, Object> map) {
        map.put("datetime", System.currentTimeMillis());
        forumMapper.insertArticle(map);
        return new R().builder();
    }

    @PostMapping("/insertComment")
    public Map<String, Object> insertComment(@RequestBody Map<String, Object> map) {
        map.put("datetime", System.currentTimeMillis());
        forumMapper.insertComment(map);
        return new R().builder();
    }

    @PostMapping("/getComment")
    public Map<String, Object> getComment(@RequestBody Map<String, Object> map) {

        return new R().add("rows",forumMapper.getComment(map)).builder();
    }

    @GetMapping("/getAnnounce")
    public Map<String, Object> getAnnounce() {
        return new R().add("rows", forumMapper.getAnnounce()).builder();
    }


}
