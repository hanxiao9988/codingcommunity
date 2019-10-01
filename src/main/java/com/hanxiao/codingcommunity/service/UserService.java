package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User selectByToken(String token) {
        User user = userMapper.selectByToken(token);
        return user;
    }

    public User selectById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }

    public void createOrUpdate(User user) {
        User user1 = userMapper.selectByAccountId(user.getAccountId());
        if (user1 == null) {
            userMapper.insert(user);
        } else {
            user1.setGmtModified(user.getGmtCreate());
            user1.setAvatarUrl(user.getAvatarUrl());
            user1.setName(user.getName());
            user1.setToken(user.getToken());
            userMapper.update(user1);
        }
    }
}
