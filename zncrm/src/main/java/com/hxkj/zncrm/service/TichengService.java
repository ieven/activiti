package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ReceiveEntity;

public interface TichengService {

    public List<ReceiveEntity> getTichengList(Map<String, String> input);

    public String getTichengCount(Map<String, String> input);

    public long addTicheng(Map<String, String> input);

    public int delTicheng(Map<String, String> input);

    public int updateTicheng(Map<String, String> input);
}
