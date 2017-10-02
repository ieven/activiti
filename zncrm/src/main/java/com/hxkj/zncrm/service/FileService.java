package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.FileEntity;

public interface FileService {

    /**
     * 根据menuId获取指定文件列表
     * 
     * @param input
     * @return
     */
    public List<FileEntity> getFilelist(Map<String, String> input);

    /**
     * 获取指定menuId的文件列表数量
     * 
     * @param menuId
     * @return
     */
    public String getFileListCount(Map<String, String> input);

    /**
     * 插入文件
     * 
     * @param entity
     * @return
     */
    public long addFile(FileEntity entity);

    /**
     * 删除文件
     * 
     * @param input
     */
    public void delFile(Map<String, String> input);

    /**
     * 根据file_id更新file_name
     * 
     * @param entity
     */
    public void setFileName(FileEntity entity);

    /**
     * 根据id获取文件信息
     * 
     * @param fileId
     * @return
     */
    public FileEntity getFileById(String fileId);

}
