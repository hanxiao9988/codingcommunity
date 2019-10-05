package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.dto.PagenationDTO;
import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.exception.CustomizeErrorCode;
import com.hanxiao.codingcommunity.exception.CustomizeException;
import com.hanxiao.codingcommunity.mapper.QuestionExtMapper;
import com.hanxiao.codingcommunity.mapper.QuestionMapper;
import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.Question;
import com.hanxiao.codingcommunity.model.QuestionExample;
import com.hanxiao.codingcommunity.model.User;
import com.hanxiao.codingcommunity.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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

    @Autowired
    QuestionExtMapper questionExtMapper;

    public void createOrUpdateQuestion(Question question) {
        Question questiondb = questionMapper.selectByPrimaryKey(question.getId());
        if (questiondb == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insertSelective(question);
        } else {
            question.setGmtModified(question.getGmtCreate());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public PagenationDTO selectQuestions(Integer currentPage, Integer size) {
        Integer offset = (currentPage - 1) * size;

        QuestionExample example = new QuestionExample();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        //计算总问题数
        Integer totalQuestionCount = (int) questionMapper.countByExample(example);

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


        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);

            questionDTO.setUser(users.get(0));
            questionDTOs.add(questionDTO);
        }
        pagenationDTO.setQuestionDTOs(questionDTOs);
        return pagenationDTO;
    }

    public Integer count() {
        QuestionExample questionExample = new QuestionExample();
        Integer totalQuestionCount = (int)questionMapper.countByExample(questionExample);
        return totalQuestionCount;
    }

    public PagenationDTO selectMyQuestions(Long userId, Integer currentPage, Integer size) {
        //计算总问题数
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalQuestionCount = (int) questionMapper.countByExample(example);

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

        //查询user的问题
        Integer offset = (currentPage - 1) * size;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));


        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        pagenationDTO.setQuestionDTOs(questionDTOs);
        return pagenationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public Question selectQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        return question;
    }

    public void incView(Long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);
    }
}
