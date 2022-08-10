package com.example.spring_seatreservation;

import com.example.spring_seatreservation.Bean.MyTask;
import com.example.spring_seatreservation.Other.DynamicTaskService;
import com.example.spring_seatreservation.controller.PublicController;
import com.example.spring_seatreservation.controller.UserController;
import com.example.spring_seatreservation.mapper.PublicMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.example.spring_seatreservation.mapper")
public class SpringSeatReservationApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringSeatReservationApplication.class, args);
    }

}
