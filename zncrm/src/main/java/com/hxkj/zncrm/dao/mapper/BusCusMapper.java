package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProjectEntity;

public interface BusCusMapper {

    public List<ProjectEntity> getProjectList(Map<String, String> input);

    public String getProjectCount(Map<String, String> input);

    public long addProject(Map<String, String> input);
}
