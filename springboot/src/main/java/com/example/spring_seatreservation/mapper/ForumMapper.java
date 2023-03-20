package com.example.spring_seatreservation.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ForumMapper {
    @Select("select * from announce order by id desc")
    List<Map<String, Object>> getAnnounce();

    @Select("select * from article order by id desc")
    List<Map<String, Object>> getArticle();

    @Insert("insert article(title,content,datetime,uid) " +
            " values(#{title},#{content},${datetime},${uid})")
    void insertArticle(Map<String, Object> map);


    @Insert("insert comments(content,uid,datetime,aid)" +
            " values(#{content},${uid},${datetime},${aid})")
    void insertComment(Map<String, Object> map);

    @Select("SELECT a.*,b.`username` FROM comments AS a LEFT JOIN USER AS b ON b.`uid`=a.`uid` WHERE aid=${aid} ORDER BY cid DESC")
    List<Map<String,Object>> getComment(Map<String, Object> map);
}
