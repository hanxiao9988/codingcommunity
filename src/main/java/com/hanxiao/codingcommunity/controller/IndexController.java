package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.PagenationDTO;
import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.User;
import com.hanxiao.codingcommunity.service.QuestionService;
import com.hanxiao.codingcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "size", defaultValue = "3") Integer size) {


        PagenationDTO pagenationDTO = questionService.selectQuestions(currentPage, size);
        model.addAttribute("pagenationDTO", pagenationDTO);

        return "index";
    }
}
