package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.FileEntity;
import com.hxkj.zncrm.dao.mapper.FileMapper;
import com.hxkj.zncrm.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper mapper;

    @Override
    public List<FileEntity> getFilelist(Map<String, String> input) {

        return mapper.getFilelist(input);
    }

    @Override
    public String getFileListCount(Map<String, String> input) {

        return mapper.getFileListCount(input);
    }

    @Override
    public long addFile(FileEntity entity) {

        return mapper.addFile(entity);
    }

    @Override
    public void delFile(Map<String, String> input) {

        mapper.delFile(input);
    }

    @Override
    public void setFileName(FileEntity entity) {

        mapper.setFileName(entity);
    }

    @Override
    public FileEntity getFileById(String fileId) {

        return mapper.getFileById(fileId);
    }

}
