package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.dto.PagenationDTO;
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

    public void createOrUpdateQuestion(Question question) {
        Question questionDB = questionMapper.getById(question.getId());
        if (questionDB == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.createQuestion(question);
        } else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.updateQuestion(question);
        }
    }

    public PagenationDTO selectQuestions(Integer currentPage, Integer size) {
        Integer offset = (currentPage - 1) * size;
        List<Question> questions = questionMapper.selectQuestions(offset, size);

        //计算总问题数
        Integer totalQuestionCount = questionMapper.count();

        //计算总页数
        Integer totalPage = 0;
        if (totalQuestionCount % size == 0) {
            totalPage = totalQuestionCount / size;
        } else {
            totalPage = totalQuestionCount / size + 1;
        }

        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        PagenationDTO pagenationDTO = new PagenationDTO();
        pagenationDTO.setPagenation(totalPage, currentPage);

        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();


        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        pagenationDTO.setQuestionDTOs(questionDTOs);
        return pagenationDTO;
    }

    public Integer count() {
        Integer totalQuestionCount = questionMapper.count();
        return totalQuestionCount;
    }

    public PagenationDTO selectMyQuestions(Integer userId, Integer currentPage, Integer size) {
        Integer offset = (currentPage - 1) * size;
        List<Question> questions = questionMapper.selectMyQuestions(userId, offset, size);

        //计算总问题数
        Integer totalQuestionCount = questionMapper.countMyQuestion(userId);

        //计算总页数
        Integer totalPage = 0;
        if (totalQuestionCount % size == 0) {
            totalPage = totalQuestionCount / size;
        } else {
            totalPage = totalQuestionCount / size + 1;
        }

        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        PagenationDTO pagenationDTO = new PagenationDTO();
        pagenationDTO.setPagenation(totalPage, currentPage);

        List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        pagenationDTO.setQuestionDTOs(questionDTOs);
        return pagenationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public Question selectQuestionById(Integer id) {
        Question question = questionMapper.getById(id);
        return question;
    }
}
