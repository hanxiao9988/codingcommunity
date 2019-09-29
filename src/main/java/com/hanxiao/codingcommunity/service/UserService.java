package com.hanxiao.codingcommunity.service;

import com.hanxiao.codingcommunity.mapper.UserMapper;
import com.hanxiao.codingcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User selectByToken(String token) {
        User user = userMapper.selectByToken(token);
        return user;
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public User selectById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }
}
