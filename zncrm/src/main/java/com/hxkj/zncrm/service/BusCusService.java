package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProjectEntity;

public interface BusCusService {

    public List<ProjectEntity> getProjectList(Map<String, String> input);

    public String getProjectCount(Map<String, String> input);
}
