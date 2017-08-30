package com.hxkj.zncrm.dao.mapper;

import com.hxkj.zncrm.po.User;

public interface UserMapper {

    int addUser();

    public User login(String username, String password);
}
