package com.example.spring_seatreservation.Bean;

public class ReservationCode {
    public static final int TIME_BEGAN = 0;//到达约定的时间，待签到
    public static final int SIGNED_BE_USE = 1;//正在使用座位
    public static final int UNSIGNED = 2;//预约未及时签到
    public static final int LEAVE = 3;//离开座位
    public static final int LEAVE_UNSIGNED = 4;//离开60分钟未及时签到
    public static final int FINISH = -1;//已完成

}
