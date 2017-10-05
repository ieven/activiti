package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.po.ChartEntity;

public interface HomeService {

    public List<ChartEntity> getChartEntityList(Map<String, String> input);
}
