package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ReceiveEntity;
import com.hxkj.zncrm.dao.mapper.TichengMapper;
import com.hxkj.zncrm.service.TichengService;

@Service
public class TichengServiceImpl implements TichengService {

    @Autowired
    private TichengMapper mapper;

    @Override
    public List<ReceiveEntity> getTichengList(Map<String, String> input) {

        return mapper.getTichengList(input);
    }

    @Override
    public String getTichengCount(Map<String, String> input) {

        return mapper.getTichengCount(input);
    }

    @Override
    public long addTicheng(Map<String, String> input) {

        return mapper.addTicheng(input);
    }

    @Override
    public int delTicheng(Map<String, String> input) {

        return mapper.delTicheng(input);
    }

    @Override
    public int updateTicheng(Map<String, String> input) {

        return mapper.updateTicheng(input);
    }

}
