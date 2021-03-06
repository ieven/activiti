package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.JobEntity;

public interface JobService {

    public List<JobEntity> getJobList(Map<String, String> input);

    public String getJobCount(Map<String, String> input);

    public long addJob(Map<String, String> input);

    public int delJob(Map<String, String> input);

    public int updateJob(Map<String, String> input);
}
