package com.example.spring_seatreservation.mapper;


import com.example.spring_seatreservation.Bean.MyUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface PublicMapper {


    @Insert("insert into user(username,password,sex,cid,type) " +
            "values(#{username},#{password},${sex},${cid},${type})")
    void insertUser(MyUser user);

    @Select("select * from user where username=#{username}")
    MyUser getUserByUserName(String username);

    @Update("update user set password=#{password} where username=#{username}")
    void updatePwd(String password, String username);

    @Select("select * from announce order by datetime desc")
    List<Map<String, Object>> getAnnounce();

    @Select("select * from sport")
    List<Map<String, Object>> getSport();

    @Select("select * from aclass")
    List<Map<String, Object>> getClassList();

    @Select("SELECT a.*,b.*,c.`cid`,u.`username`,d.`name` AS cname FROM score AS a\n" +
            " LEFT JOIN sport AS b ON a.`sid`=b.`sid` \n" +
            " LEFT JOIN task AS c ON a.`tid`=c.`tid` \n" +
            " LEFT JOIN aclass AS d ON d.`cid`=c.`cid` \n" +
            " LEFT JOIN USER AS u ON u.`uid`=a.`uid` \n" +
            " WHERE a.`score`IS NOT NULL \n" +
            " ORDER BY a.`sid` DESC")
    List<Map<String, Object>> getStatistic();
}
