package com.example.spring_seatreservation.Other;

import java.util.Map;

public class SignedNumber {
    private static final long KEY = 123456;

    /**
     * 用座位id、用户id、预约开始时间后6位相加进行加密
     *
     * @param map
     * @return
     */
    public static long getSignedNumber(Map<String, Object> map) {
        long sid = new Long(map.get("sid").toString());
        long uid = new Long(map.get("uid").toString());
        long startTime = new Long(map.get("startTime").toString()) % 1000000;
        return KEY * (sid + uid + startTime) % 1000000;
    }

    /**
     * 用座位id、用户id、暂离时间后6位相加进行加密
     *
     * @param map
     * @return
     */
    public static long getLeaveSignedNumber(Map<String, Object> map) {
        long sid = new Long(map.get("sid").toString());
        long uid = new Long(map.get("uid").toString());
        long leaveTime = new Long(map.get("leaveTime").toString()) % 1000000;
        return KEY * (sid + uid + leaveTime) % 1000000;
    }


}
