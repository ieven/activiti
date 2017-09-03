package com.hxkj.zncrm.dao.mapper;

import java.util.Map;

import com.hxkj.zncrm.po.User;

public interface UserMapper {

    int addUser();

    public User login(Map<String, String> map);
}
