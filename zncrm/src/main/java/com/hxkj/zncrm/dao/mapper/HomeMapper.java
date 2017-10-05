package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.po.ChartEntity;

public interface HomeMapper {

    public List<ChartEntity> getChartEntityList(Map<String, String> input);

}
