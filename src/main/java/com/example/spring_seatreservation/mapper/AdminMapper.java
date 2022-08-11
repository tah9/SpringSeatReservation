package com.example.spring_seatreservation.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
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

    @Delete("delete from article where id=${id}")
    void deleteArticle(Map<String, Object> map);

    @Delete("delete from announce where id=${id}")
    void deleteAnnounce(Map<String, Object> map);

    @Select("select * from user where type=0")
    List<Map<String, Object>> getUser();

    @Select("select * from user where type=1")
    List<Map<String, Object>> getTeacher();

    @Update("update user set password =#{password} where uid=${uid}")
    void updatePassword(Map<String, Object> map);

    @Select("SELECT `startTime`,`endTime` FROM reservation  WHERE state=-1 ORDER BY startTime")
    List<Map<String, Object>> getStatistics();

    @Select("SELECT COUNT(a.`uid`) as counter,b.`number`,b.`username` FROM reservation AS a \n" +
            "LEFT JOIN USER AS b ON b.`uid`=a.`uid`\n" +
            "WHERE a.`state`=-1 GROUP BY a.`uid` ORDER BY COUNT(a.`uid`)")
    List<Map<String, Object>> getUserCounter();
}
