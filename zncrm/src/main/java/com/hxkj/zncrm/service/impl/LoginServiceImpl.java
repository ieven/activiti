package com.hxkj.zncrm.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.mapper.UserMapper;
import com.hxkj.zncrm.po.User;
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.util.PropertiesHelper;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private PropertiesHelper util;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {

        User user = userMapper.login(username, password);
        if (user == null) {
            return null;
        }
        else {
            String filePath = System.getProperty("hxkj.root") + "/../zncrm.properties";
            try {
                util.WriteProperties(filePath, "username", username);
                util.WriteProperties(filePath, "password", password);
            }
            catch (IOException e) {
                logger.error(e.toString());
            }
            return user;
        }
    }

}
