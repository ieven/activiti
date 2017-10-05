package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.mapper.HomeMapper;
import com.hxkj.zncrm.po.ChartEntity;
import com.hxkj.zncrm.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper mapper;

    @Override
    public List<ChartEntity> getChartEntityList(Map<String, String> input) {

        return mapper.getChartEntityList(input);
    }

}
