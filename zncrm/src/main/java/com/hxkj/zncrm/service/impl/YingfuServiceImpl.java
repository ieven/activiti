package com.hxkj.zncrm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.ReceiveEntity;
import com.hxkj.zncrm.dao.mapper.YingfuMapper;
import com.hxkj.zncrm.service.YingfuService;

@Service
public class YingfuServiceImpl implements YingfuService {

    @Autowired
    private YingfuMapper mapper;

    @Override
    public List<ReceiveEntity> getPaymentList(Map<String, String> input) {

        return mapper.getPaymentList(input);
    }

    @Override
    public String getPaymentCount(Map<String, String> input) {

        return mapper.getPaymentCount(input);
    }

    @Override
    public long addPayment(Map<String, String> input) {

        return mapper.addPayment(input);
    }

    @Override
    public int delPayment(Map<String, String> input) {

        return mapper.delPayment(input);
    }

    @Override
    public int updatePayment(Map<String, String> input) {

        return mapper.updatePayment(input);
    }

}
