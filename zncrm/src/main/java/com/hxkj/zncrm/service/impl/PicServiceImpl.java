package com.hxkj.zncrm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.mapper.PicMapper;
import com.hxkj.zncrm.service.PicService;

@Service
public class PicServiceImpl implements PicService {

    @Autowired
    private PicMapper picMapper;

    @Override
    public byte[] getPicById(String id) {

        Map<String, Object> map = picMapper.getPicById(id);
        byte[] data = (byte[]) map.get("imgBytes");
        return data;
    }

}
