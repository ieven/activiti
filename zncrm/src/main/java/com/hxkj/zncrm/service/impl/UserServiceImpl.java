package com.hxkj.zncrm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.mapper.UserMapper;
import com.hxkj.zncrm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser() {

        return userMapper.addUser();
    }
}
