package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProLib;

public interface ProLibMapper {

    /**
     * 根据条件查询产品库列表
     * 
     * @param param
     * @return
     */
    public List<ProLib> getProLib(Map<String, String> param);

    /**
     * 根据条件查询产品库列表数量
     * 
     * @param param
     * @return
     */
    public String getProLibCount(Map<String, String> param);
}
