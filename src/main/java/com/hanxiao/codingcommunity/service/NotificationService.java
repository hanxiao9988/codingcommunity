package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.dto.NotificationDTO;
import com.hanxiao.codingcommunity.dto.PagenationDTO;
import com.hanxiao.codingcommunity.dto.QuestionDTO;
import com.hanxiao.codingcommunity.enums.NotificationStatusEnum;
import com.hanxiao.codingcommunity.exception.CustomizeErrorCode;
import com.hanxiao.codingcommunity.exception.CustomizeException;
import com.hanxiao.codingcommunity.mapper.NotificationMapper;
import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.*;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PagenationDTO list(Long userId, Integer currentPage, Integer size) {

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        Integer totalQuestionCount = (int) notificationMapper.countByExample(example);

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

        PagenationDTO<NotificationDTO> pagenationDTO = new PagenationDTO();
        pagenationDTO.setPagenation(totalPage, currentPage);

        Integer offset = (currentPage - 1) * size;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper
                .selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return pagenationDTO;
        }

        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTOs.add(notificationDTO);

        }

        pagenationDTO.setData(notificationDTOs);
        return pagenationDTO;


    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        return notificationDTO;
    }
}
