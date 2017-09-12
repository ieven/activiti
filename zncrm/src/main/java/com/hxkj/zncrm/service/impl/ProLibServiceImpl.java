package com.hxkj.zncrm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ProLib;
import com.hxkj.zncrm.dao.mapper.ProLibMapper;
import com.hxkj.zncrm.service.ProLibService;

@Service
public class ProLibServiceImpl implements ProLibService {

    @Autowired
    private ProLibMapper mapper;

    @Override
    public List<ProLib> getProLib(Map input) {

        return mapper.getProLib(input);
    }

    @Override
    public String getProLibCount(Map input) {

        return mapper.getProLibCount(input);
    }

    @Override
    public int updateProLib(Map input) {

        return mapper.updateProLib(input);
    }

    @Override
    public int delProLib(Map input) {

        return mapper.delProLib(input);
    }

    @Override
    public long addProLib(Map input) {

        return mapper.addProLib(input);
    }

    @Override
    public byte[] getProLibPic(String id) {

        Map<String, Object> map = mapper.getProLibPic(id);
        if (map == null || map.isEmpty()) {
            return null;
        }
        byte[] data = (byte[]) map.get("imgBytes");
        return data;
    }

    @Override
    public int addProLibPic(String proId, byte[] bs) {

        Map map = new HashMap<>();
        map.put("pro_id", proId);
        map.put("pro_pic", bs);
        return mapper.addProLibPic(map);
    }

    @Override
    public ProLib getProLibById(String proId) {

        return mapper.getProLibById(proId);
    }

}
