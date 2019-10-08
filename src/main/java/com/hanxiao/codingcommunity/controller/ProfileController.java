package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.PagenationDTO;
import com.hanxiao.codingcommunity.model.User;
import com.hanxiao.codingcommunity.service.NotificationService;
import com.hanxiao.codingcommunity.service.QuestionService;
import com.hanxiao.codingcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "3") Integer size,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }


        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");

            PagenationDTO pagenationDTO = questionService.selectMyQuestions(user.getId(), currentPage, size);
            model.addAttribute("pagenationDTO", pagenationDTO);
        } else if ("replies".equals(action)) {
            PagenationDTO pagenationDTO = notificationService.list(user.getId(), currentPage, size);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
            model.addAttribute("pagenationDTO", pagenationDTO);

        }

        return "profile";
    }
}
