package com.hanxiao.codingcommunity.controller;

import com.hanxiao.codingcommunity.dto.CommentCreateDTO;
import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.dto.ResultDTO;
import com.hanxiao.codingcommunity.exception.CustomizeErrorCode;
import com.hanxiao.codingcommunity.model.Comment;
import com.hanxiao.codingcommunity.model.User;
import com.hanxiao.codingcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment", method= RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }


        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        //Map<Object, Object> map = new HashMap<>();
        //map.put("message", "成功");
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

//    @GetMapping(value = "")
//    public List<QuestionDTO> selectComments() {
//
//    }
}
