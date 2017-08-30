package com.hxkj.zncrm.service;

import com.hxkj.zncrm.po.User;

public interface LoginService {

    public User login(String username, String password);
}
