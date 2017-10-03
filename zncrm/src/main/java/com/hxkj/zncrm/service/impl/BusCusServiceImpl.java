package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.dao.mapper.BusCusMapper;
import com.hxkj.zncrm.service.BusCusService;

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

}
