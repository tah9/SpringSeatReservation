package com.example.spring_seatreservation.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface UserMapper {


    @Update("update user set height=${height},weight=${weight} where uid=${uid}")
    void updateMess(Map<String, Object> map);

    @Select("select height,weight from user where uid=${uid}")
    Map<String, Object> getMess(Map<String, Object> map);

    @Select("SELECT a.*,b.`name` AS sname,c.`name` AS cname FROM task AS a" +
            " LEFT JOIN sport AS b ON a.`sid`=b.`sid` " +
            " LEFT JOIN aclass AS c ON c.`cid`=a.`cid`" +
            " where b.`sex`=${sex} and a.`cid`=${cid}" +
            " order by a.`datetime` desc")
    List<Map<String, Object>> getTask(Map<String, Object> map);

    @Select("select * from sport where sid=${sid}")
    Map<String, Object> getSport(Map<String, Object> map);


    @Insert("insert score (tid,uid,sid)" +
            " values(${tid},${uid},${sid})")
    void join(Map<String, Object> map);

    @Select("SELECT EXISTS(SELECT id FROM score WHERE uid=${uid} AND tid=${tid} LIMIT 1)")
    boolean getScore(Map<String, Object> map);

    @Select("SELECT a.*,b.*,c.*,t.`name` AS tname FROM score AS a\n" +
            " LEFT JOIN USER AS b ON a.`uid`=b.`uid`\n" +
            " LEFT JOIN task AS t ON a.`tid`=t.`tid`\n" +
            " LEFT JOIN sport AS c ON c.`sid`=a.`sid` WHERE a.`uid`=${uid} ORDER BY a.`id` DESC")
    List<Map<String, Object>> getScoreByUid(Map<String, Object> map);
}
