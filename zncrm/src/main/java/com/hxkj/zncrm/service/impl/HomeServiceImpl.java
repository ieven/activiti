package com.hxkj.zncrm.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
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

        // 获取销售部和业务部的名单
        List<String> nameList = mapper.getNameList();
        List<ChartEntity> entityList = mapper.getChartEntityList(input);
        // 离职名单
        List<String> quitList = mapper.getQuitList();
        // 创建一个临时的map，用来作为记录指针
        Map<String, ChartEntity> temp = new HashMap<String, ChartEntity>();
        for (ChartEntity entity : entityList) {
            if (!quitList.contains(entity.getRecorder())) {
                temp.put(entity.getRecorder(), entity);
            }
        }
        // 检查人员是否都在，不在的则添加
        for (String name : nameList) {
            if (!temp.containsKey(name)) {
                ChartEntity entity = new ChartEntity();
                entity.setRecorder(name);
                entity.setNum("0");
                temp.put(name, entity);
            }
        }
        List<ChartEntity> result = new LinkedList<>();
        result.addAll(temp.values());
        return result;
    }

}
