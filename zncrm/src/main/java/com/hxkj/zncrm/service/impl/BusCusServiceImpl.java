package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.dao.domain.ProjectRecordEntity;
import com.hxkj.zncrm.dao.mapper.BusCusMapper;
import com.hxkj.zncrm.service.BusCusService;
import com.hxkj.zncrm.util.StringHelper;

@Service
public class BusCusServiceImpl implements BusCusService {

    @Autowired
    private BusCusMapper mapper;

    @Override
    public List<ProjectEntity> getProjectList(Map<String, String> input) {

        return mapper.getProjectList(input);
    }

    @Override
    public String getProjectCount(Map<String, String> input) {

        return mapper.getProjectCount(input);
    }

    @Override
    public long addProject(Map<String, String> input) {

        // 特殊处理一下客户需求
        String xuqiu = input.get("cus_require");
        if (!StringHelper.isEmpty(xuqiu)) {
            input.put("cus_require", xuqiu.substring(1, xuqiu.length()));
        }
        return mapper.addProject(input);
    }

    @Override
    public ProjectEntity getProject(Map<String, String> input) {

        return mapper.getProject(input);
    }

    @Override
    public List<ProjectRecordEntity> getProjectLog(Map<String, String> input) {

        return mapper.getProjectLog(input);
    }

    @Override
    public String getProjectLogCount(Map<String, String> input) {

        return mapper.getProjectLogCount(input);
    }

    @Override
    public int delProjectLog(Map<String, String> input) {

        return mapper.delProjectLog(input);
    }

    @Override
    public int updateProject(Map<String, String> input) {

        return mapper.updateProject(input);
    }

    @Override
    public long addProjectLog(Map<String, String> input) {

        return mapper.addProjectLog(input);
    }

}
