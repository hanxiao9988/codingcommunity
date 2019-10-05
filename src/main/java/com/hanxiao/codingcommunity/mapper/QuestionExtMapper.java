package com.hanxiao.codingcommunity.mapper;

import com.hanxiao.codingcommunity.model.Question;
import org.apache.ibatis.annotations.Mapper;


public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);

}
