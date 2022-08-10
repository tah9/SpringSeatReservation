package com.example.spring_seatreservation.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface UserMapper {

    //插入后，得到刚插入的数据id(在承载实体里面)
    @Insert("insert reservation (uid,startTime,endTime,sid)" +
            " values(${uid},${startTime},${endTime},${sid})")
    @Options(useGeneratedKeys = true, keyProperty = "rid")
    void addReservation(Map<String, Object> map);


    @Update("update reservation set state=${state} where rid=${rid}")
    void updateReservation(Object state, Object rid);

    @Select("select * from reservation where rid=${rid}")
    Map<String, Object> getReservationByRid(Object rid);
}
