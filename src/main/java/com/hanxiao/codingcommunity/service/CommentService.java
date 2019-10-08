package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.dto.CommentDTO;
import com.hanxiao.codingcommunity.enums.NotificationStatusEnum;
import com.hanxiao.codingcommunity.exception.CustomizeErrorCode;
import com.hanxiao.codingcommunity.exception.CustomizeException;
import com.hanxiao.codingcommunity.mapper.*;
import com.hanxiao.codingcommunity.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        commentMapper.insert(comment);
        question.setCommentCount(1);
        questionExtMapper.incCommentCount(question);


        if (question.getCreator() == comment.getCommentator()) {
            return;
        }

        //创建通知
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setOuterId(question.getId());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(question.getCreator());
        notification.setNotifierName(commentator.getName());
        notification.setOuterTitle(question.getTitle());
        notificationMapper.insert(notification);

    }




    public List<CommentDTO> ListByTargetId(Long id) {
        //查询当前问题下的所有评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id);
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        //无评论返回空的ArrayList
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //commentators即userIds（用户id），获取所有用户id并去重
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);

        //根据userIds提供的id范围，查询对应的所有user（users）
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);

        //用stream流获取一个以userId为键，User为值的Map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //把comment的所有属性填入commentDTO中
        //获取comment的commentator属性，在userMap中通过commentator获取User对象实例
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;

        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
