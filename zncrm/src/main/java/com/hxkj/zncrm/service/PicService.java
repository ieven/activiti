package com.hxkj.zncrm.service;

public interface PicService {

    /**
     * 根据图片id获取人物头像
     * 
     * @param id
     * @return
     */
    public byte[] getPicById(String id);
}
