package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.NotificationDTO;
import com.hanxiao.codingcommunity.dto.PagenationDTO;
import com.hanxiao.codingcommunity.model.Notification;
import com.hanxiao.codingcommunity.model.User;
import com.hanxiao.codingcommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        return "redirect:/question/" + notificationDTO.getOuterId();
    }


}
