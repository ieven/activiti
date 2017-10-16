package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.JobEntity;
import com.hxkj.zncrm.dao.domain.RoleEntity;
import com.hxkj.zncrm.po.RoleAuthEntity;
import com.hxkj.zncrm.po.User;

public interface LoginService {

    public User login(String username, String password);

    public List<User> getUserList(Map<String, String> map);

    public String getUserCount(Map<String, String> map);

    public List<JobEntity> getJobList();

    public List<RoleEntity> getRoleList();

    public String addUser(Map<String, String> map);

    public int delUser(Map<String, String> input);

    public int updateUser(Map<String, String> input);

    public User getUserByUsername(Map<String, String> input);

    public List<RoleEntity> getRoleListPage(Map<String, String> input);

    public String getRoleListPageCount(Map<String, String> input);

    public String addUserMenu(Map<String, String> input);

    public RoleAuthEntity getRoleAuth(Map<String, String> input);

    public String updateUserMenu(Map<String, String> input);

    public List<String> getRoleOperAuth(Map<String, String> input);
}
