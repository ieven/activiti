package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.GroupMenuEntity;
import com.hxkj.zncrm.dao.domain.JobEntity;
import com.hxkj.zncrm.dao.domain.RoleEntity;
import com.hxkj.zncrm.po.User;

public interface UserMapper {

    public User login(Map<String, String> map);

    public List<User> getUserList(Map<String, String> map);

    public String getUserCount(Map<String, String> map);

    public List<JobEntity> getJobList();

    public List<RoleEntity> getRoleList();

    public void addUser(Map<String, String> map);

    public int delUser(Map<String, String> input);

    public int updateUser(Map<String, String> input);

    public User getUserByUsername(Map<String, String> input);

    public List<RoleEntity> getRoleListPage(Map<String, String> input);

    public String getRoleListPageCount(Map<String, String> input);

    public int delMenuByRole(Map<String, String> input);

    public int updateRole(Map<String, String> input);

    public int updateMembership(Map<String, String> input);

    public int addMembership(Map<String, String> input);

    public int addRoleMenu(List<GroupMenuEntity> list);

    public void addRole(Map<String, String> input);

    public String getAuthById(Map<String, String> input);

    public int delRole(Map<String, String> input);

    public List<User> getUserListByDepartment(Map<String, String> input);

    public String getUserDepartmentCount(Map<String, String> input);

    public List<User> getUserListByStatus(Map<String, String> input);

    public String getUserStatusCount(Map<String, String> input);

    public List<User> getManagerUsers(Map<String, String> input);

    public String getManagerUsersCount(Map<String, String> input);
}
