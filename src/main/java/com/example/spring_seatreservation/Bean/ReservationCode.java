package com.example.spring_seatreservation.Bean;

public class ReservationCode {
    public static final int WAIT_SIGNED = 0;//待签到
    public static final int SIGNED_BE_USE = 1;//使用中
    public static final int UNSIGNED = 2;//预约未及时签到
    public static final int LEAVE = 3;//离开
    public static final int LEAVE_UNSIGNED = 4;//离开60分钟未及时签到
    public static final int FINISH = -1;//已完成

}
