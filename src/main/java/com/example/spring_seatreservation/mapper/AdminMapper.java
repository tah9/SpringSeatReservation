package com.example.spring_seatreservation.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface AdminMapper {
    @Update("update sport set full=${full},excellence=${excellence},good=${good},pass=${pass},fail=${fail}" +
            " where sid=${sid}")
    void updateSport(Map<String, Object> map);

    @Insert("insert announce(content,title,datetime)" +
            " values(#{content},#{title},${datetime})")
    void addAnnounce(Map<String, Object> map);

    @Insert("insert seat(`area`,`type`,`row`,`column`)" +
            " values(${area},${type},${row},${column})")
    void addSeat(Map<String, Object> map);


    @Delete("delete from seat where sid=${sid}")
    void deleteSeat(Map<String, Object> map);
}
