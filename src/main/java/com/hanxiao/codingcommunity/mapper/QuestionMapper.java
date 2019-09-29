package com.hanxiao.codingcommunity.mapper;

import com.hanxiao.codingcommunity.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator," +
            "tag) values (#{id}, #{title}, #{description}, #{gmtCreate}," +
            "#{gmtModified}, #{creator}, #{tag})")
    void createQuestion(Question question);

    @Select("select * from question")
    List<Question> selectAllQuestions();

}
