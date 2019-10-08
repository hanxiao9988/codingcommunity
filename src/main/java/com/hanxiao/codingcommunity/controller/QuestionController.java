package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.CommentDTO;
import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.service.CommentService;
import com.hanxiao.codingcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     * 需要根据问题id获取问题的全部信息
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        //查询当前问题
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        questionService.incView(id);

        //查询问题对应的所有评论
        List<CommentDTO> commentDTOs = commentService.ListByTargetId(id);
        model.addAttribute("commentDTOs", commentDTOs);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";

    }

}
