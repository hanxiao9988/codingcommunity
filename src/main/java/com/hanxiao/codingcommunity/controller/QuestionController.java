package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.model.Question;
import com.hanxiao.codingcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     * 需要根据问题id获取问题的全部信息
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id,
                           Model model) {

        Integer questionId = Integer.valueOf(id);
        QuestionDTO questionDTO = questionService.getById(questionId);
        model.addAttribute("questionDTO", questionDTO);
        return "question";

    }

}
