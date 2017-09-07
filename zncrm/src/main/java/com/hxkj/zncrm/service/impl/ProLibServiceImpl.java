package com.hxkj.zncrm.service.impl;

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

}
