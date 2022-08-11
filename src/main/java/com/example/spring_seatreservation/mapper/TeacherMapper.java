package com.example.spring_seatreservation.mapper;


import com.example.spring_seatreservation.Bean.R;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface TeacherMapper {

    @Select("SELECT a.*,b.*,c.*,d.*,d.`score` as uScore FROM reservation AS a \n" +
            "            LEFT JOIN  seat AS b ON b.`sid`=a.`sid` \n" +
            "            LEFT JOIN `area` AS c ON c.`aid`=b.`area`\n" +
            "            LEFT JOIN USER AS d ON d.`uid`=a.`uid`\n" +
            "            ORDER BY rid DESC")
    List<Map<String, Object>> getReservation();

    @Select("SELECT a.*,b.* FROM reservation AS a LEFT JOIN USER AS b ON b.`uid`=a.`uid` WHERE state=2 OR state=4")
    List<Map<String, Object>> getReservationNeedSub();

    @Update("update user set `score`=score-10 where  uid=${uid}")
    void subScore(Object uid);

    @Update("update reservation set `score`=10 where rid=${rid}")
    void subReservationScore(Object rid);


}
