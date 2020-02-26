package com.mylawyer.community.mapper;

import com.mylawyer.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,creator,gmt_create,gmt_modified,tag) values (#{title},#{description},#{creator},#{gmtCreate},#{gmtModified},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{creator} limit #{offset},#{size}")
    List<Question> listQuestionByCreator(@Param("creator") Integer creator, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{creator}")
    Integer countByCreator(@Param("creator") Integer creator);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Integer id);
}
