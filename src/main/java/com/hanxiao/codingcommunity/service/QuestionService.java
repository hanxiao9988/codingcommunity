package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.mapper.QuestionMapper;
import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.Question;
import com.hanxiao.codingcommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public void createQuestion(Question question) {
        questionMapper.createQuestion(question);
    }

    public List<QuestionDTO> selectAllQuestions() {
        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();;
        List<Question> questions = questionMapper.selectAllQuestions();

        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
