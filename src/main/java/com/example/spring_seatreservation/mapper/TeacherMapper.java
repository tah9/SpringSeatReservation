package com.example.spring_seatreservation.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    @Select("SELECT a.*,b.`name` AS sname,b.*,c.`name` AS cname FROM task AS a LEFT JOIN sport AS b ON a.`sid`=b.`sid`  LEFT JOIN aclass AS c ON c.`cid`=a.`cid`" +
            " order by a.`datetime` desc")
    List<Map<String, Object>> getTask();

    @Insert("insert task(cid,sid,datetime,name)" +
            " values(${cid},${sid},${datetime},#{name})")
    void insertTask(Map<String, Object> map);


    @Select("SELECT a.*,b.* FROM score AS a LEFT JOIN USER AS b ON a.`uid` = b.`uid`  WHERE tid=${tid}")
    List<Map<String, Object>> getScoreByTid(Map<String, Object> map);

    @Update("update score set `score`=${score} where tid=${tid} and uid=${uid}")
    void updateScore(Map<String, Object> map);
}
