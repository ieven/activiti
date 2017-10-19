package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.JobEntity;
import com.hxkj.zncrm.dao.mapper.JobMapper;
import com.hxkj.zncrm.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public List<JobEntity> getJobList(Map<String, String> input) {

        return jobMapper.getJobList(input);
    }

    @Override
    public String getJobCount(Map<String, String> input) {

        return jobMapper.getJobCount(input);
    }

    @Override
    public long addJob(Map<String, String> input) {

        return jobMapper.addJob(input);
    }

    @Override
    public int delJob(Map<String, String> input) {

        return jobMapper.delJob(input);
    }

    @Override
    public int updateJob(Map<String, String> input) {

        return jobMapper.updateJob(input);
    }

}
