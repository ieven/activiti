package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ReceiveEntity;
import com.hxkj.zncrm.dao.mapper.YingshouMapper;
import com.hxkj.zncrm.service.YingshouService;

@Service
public class YingshouServiceImpl implements YingshouService {

    @Autowired
    private YingshouMapper mapper;

    @Override
    public List<ReceiveEntity> getReceiveList(Map<String, String> input) {

        return mapper.getReceiveList(input);
    }

    @Override
    public String getReceiveCount(Map<String, String> input) {

        return mapper.getReceiveCount(input);
    }

    @Override
    public long addReceive(Map<String, String> input) {

        return mapper.addReceive(input);
    }

    @Override
    public int delReceive(Map<String, String> input) {

        return mapper.delReceive(input);
    }

    @Override
    public int updateReceive(Map<String, String> input) {

        return mapper.updateReceive(input);
    }

}
