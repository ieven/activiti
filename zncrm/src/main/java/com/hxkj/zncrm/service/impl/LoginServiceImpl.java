package com.hxkj.zncrm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hxkj.zncrm.dao.domain.GroupMenuEntity;
import com.hxkj.zncrm.dao.domain.JobEntity;
import com.hxkj.zncrm.dao.domain.RoleEntity;
import com.hxkj.zncrm.dao.mapper.MenuMapper;
import com.hxkj.zncrm.dao.mapper.UserMapper;
import com.hxkj.zncrm.po.RoleAuthEntity;
import com.hxkj.zncrm.po.User;
import com.hxkj.zncrm.service.LoginService;
import com.hxkj.zncrm.util.JSONHelper;
import com.hxkj.zncrm.util.PropertiesHelper;

@Service
public class LoginServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private PropertiesHelper util;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public User login(String username, String password) {

        Map<String, String> map = new HashMap<>(2);
        map.put("username", username);
        map.put("password", password);
        User user = userMapper.login(map);
        if (user == null) {
            return null;
        }
        else {
            String filePath = System.getProperty("hxkj.zncrm.root") + "/../zncrm" + username + ".properties";
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

    @Override
    public List<User> getUserList(Map<String, String> map) {

        return userMapper.getUserList(map);
    }

    @Override
    public String getUserCount(Map<String, String> map) {

        return userMapper.getUserCount(map);
    }

    @Override
    public List<JobEntity> getJobList() {

        return userMapper.getJobList();
    }

    @Override
    public List<RoleEntity> getRoleList() {

        return userMapper.getRoleList();
    }

    @Override
    public String addUser(Map<String, String> map) {

        userMapper.addUser(map);
        userMapper.addMembership(map);
        return "";
    }

    @Override
    public int delUser(Map<String, String> input) {

        return userMapper.delUser(input);
    }

    @Override
    public int updateUser(Map<String, String> input) {

        if (userMapper.updateMembership(input) == 0) {
            userMapper.addMembership(input);
        }
        return userMapper.updateUser(input);
    }

    @Override
    public User getUserByUsername(Map<String, String> input) {

        return userMapper.getUserByUsername(input);
    }

    @Override
    public List<RoleEntity> getRoleListPage(Map<String, String> input) {

        return userMapper.getRoleListPage(input);
    }

    @Override
    public String getRoleListPageCount(Map<String, String> input) {

        return userMapper.getRoleListPageCount(input);
    }

    @Override
    public String addUserMenu(Map input) {

        JSONArray json = (JSONArray) input.get("menu_param");

        List<JSONObject> list = JSONHelper.toObject(json.toJSONString(), List.class);
        List<GroupMenuEntity> inputList = new ArrayList<>();
        for (JSONObject entity : list) {
            GroupMenuEntity temp = new GroupMenuEntity();
            temp.setGroup_id(entity.getString("group_id"));
            temp.setMenu_id(entity.getString("menu_id"));
            inputList.add(temp);
        }
        userMapper.addRole(input);
        userMapper.addRoleMenu(inputList);
        return "";
    }

    @Override
    public RoleAuthEntity getRoleAuth(Map<String, String> input) {

        List<String> menuList = menuMapper.getMeunListByRole(input);
        String authorities = userMapper.getAuthById(input);
        String[] temp = authorities.split(",");
        List<String> authList = new ArrayList<>();
        for (String str : temp) {
            authList.add(str);
        }
        RoleAuthEntity result = new RoleAuthEntity();
        result.setMenuList(menuList);
        result.setAuthorities(authList);
        return result;
    }

    @Override
    public String updateUserMenu(Map input) {

        JSONArray json = (JSONArray) input.get("menu_param");

        List<JSONObject> list = JSONHelper.toObject(json.toJSONString(), List.class);
        List<GroupMenuEntity> inputList = new ArrayList<>();
        for (JSONObject entity : list) {
            GroupMenuEntity temp = new GroupMenuEntity();
            temp.setGroup_id(entity.getString("group_id"));
            temp.setMenu_id(entity.getString("menu_id"));
            inputList.add(temp);
        }
        userMapper.updateRole(input);
        userMapper.delMenuByRole(input);
        userMapper.addRoleMenu(inputList);
        return null;
    }

    @Override
    public List<String> getRoleOperAuth(Map<String, String> input) {

        String authorities = userMapper.getAuthById(input);
        String[] temp = authorities.split(",");
        List<String> authList = new ArrayList<>();
        for (String str : temp) {
            authList.add(str);
        }
        return authList;
    }

    @Override
    public int delRole(Map<String, String> input) {

        return userMapper.delRole(input);
    }

    @Override
    public List<User> getUserListByDepartment(Map<String, String> input) {

        return userMapper.getUserListByDepartment(input);
    }

    @Override
    public String getUserDepartmentCount(Map<String, String> input) {

        return userMapper.getUserDepartmentCount(input);
    }

    @Override
    public List<User> getUserListByStatus(Map<String, String> input) {

        return userMapper.getUserListByStatus(input);
    }

    @Override
    public String getUserStatusCount(Map<String, String> input) {

        return userMapper.getUserStatusCount(input);
    }

    @Override
    public List<User> getManagerUsers(Map<String, String> input) {

        return userMapper.getManagerUsers(input);
    }

    @Override
    public String getManagerUsersCount(Map<String, String> input) {

        return userMapper.getManagerUsersCount(input);
    }

}
