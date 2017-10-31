package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProjectEntity;
import com.hxkj.zncrm.dao.domain.ProjectRecordEntity;
import com.hxkj.zncrm.po.BusCusMenuListEntity;

public interface BusCusMapper {

    public List<ProjectEntity> getProjectList(Map<String, String> input);

    public String getProjectCount(Map<String, String> input);

    public long addProject(Map<String, String> input);

    public ProjectEntity getProject(Map<String, String> input);

    public List<ProjectRecordEntity> getProjectLog(Map<String, String> input);

    public String getProjectLogCount(Map<String, String> input);

    public int delProjectLog(Map<String, String> input);

    public int updateProject(Map<String, String> input);

    public long addProjectLog(Map<String, String> input);

    public int updateProjectLog(Map<String, String> input);

    public List<BusCusMenuListEntity> getBusCusMenuList(Map<String, String> input);

    public List<ProjectEntity> searchProjectList(Map<String, String> input);

    public String searchProjectListCount(Map<String, String> input);
}
