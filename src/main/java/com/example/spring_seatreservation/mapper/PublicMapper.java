package com.example.spring_seatreservation.mapper;


import com.example.spring_seatreservation.Bean.MyUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface PublicMapper {


    @Insert("insert into user(number,username,password,type) " +
            "values(${number},#{username},#{password},${type})")
    void insertUser(MyUser user);

    @Select("select * from user where number=${number}")
    MyUser getUserByNumber(long number);

    @Update("update user set password=#{password} where number=#{number}")
    void updatePwd(String password, long number);

    @Select("select * from area")
    List<Map<String, Object>> getArea();

    @Select("select * from seat where area=${area}")
    List<Map<String, Object>> getAreaSeats(Map<String, Object> map);

    @Select("select * from reservation where state=0 or state=3")
    List<Map<String, Object>> getWaitReservation();

    @Update("update reservation set state=${state} where rid=${rid}")
    void updateReservation(Object state, Object rid);

    @Select("select * from reservation where rid=${rid}")
    Map<String, Object> getReservationByRid(Object rid);
}
