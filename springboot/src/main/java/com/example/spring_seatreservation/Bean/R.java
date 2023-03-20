package com.example.spring_seatreservation.Bean;

import java.util.HashMap;


public class R extends HashMap<String,Object> {
    HashMap<String,Object> map;

    public R() {
        map = new HashMap<>();
    }

    public HashMap<String,Object> builder() {
        return map;
    }

    public R ok() {
        map.put("code", 200);
        return this;
    }

    public R add(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public R bad() {
        map.put("code", 400);
        return this;
    }
}
