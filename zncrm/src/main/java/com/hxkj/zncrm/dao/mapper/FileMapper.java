package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.FileEntity;

public interface FileMapper {

    public List<FileEntity> getFilelist(Map<String, String> input);

    public String getFileListCount(Map<String, String> input);

    public long addFile(FileEntity entity);

    public void delFile(Map<String, String> input);

    public void setFileName(FileEntity entity);

    public FileEntity getFileById(String fileId);
}
