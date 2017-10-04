package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.dao.domain.ProjectRecordEntity;

public interface BusCusService {

    public List<ProjectEntity> getProjectList(Map<String, String> input);

    public String getProjectCount(Map<String, String> input);

    public long addProject(Map<String, String> input);

    public ProjectEntity getProject(Map<String, String> input);

    public List<ProjectRecordEntity> getProjectLog(Map<String, String> input);

    public String getProjectLogCount(Map<String, String> input);

    public int delProjectLog(Map<String, String> input);

    public int updateProject(Map<String, String> input);

    public long addProjectLog(Map<String, String> input);
}
