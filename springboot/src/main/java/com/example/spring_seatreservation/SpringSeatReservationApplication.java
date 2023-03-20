package com.example.spring_seatreservation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.example.spring_seatreservation.mapper")
public class SpringSeatReservationApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringSeatReservationApplication.class, args);
    }

}
