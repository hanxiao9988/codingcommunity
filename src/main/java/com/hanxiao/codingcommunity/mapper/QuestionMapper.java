package com.hanxiao.codingcommunity.mapper;

import com.hanxiao.codingcommunity.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator," +
            "tag) values (#{id}, #{title}, #{description}, #{gmtCreate}," +
            "#{gmtModified}, #{creator}, #{tag})")
    void createQuestion(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> selectQuestions(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> selectMyQuestions(@Param("userId") Integer userId,
                                     @Param("offset") Integer offset,
                                     @Param("size")Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countMyQuestion(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, tag = #{tag} where id = #{id}")
    void updateQuestion(Question question);
}
