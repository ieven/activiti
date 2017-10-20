package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.LinkManEntity;
import com.hxkj.zncrm.dao.domain.ProLib;
import com.hxkj.zncrm.po.ProNameEntity;

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

    /**
     * 更新指定id的产品库
     * 
     * @param input
     * @return
     */
    public int updateProLib(Map<String, String> input);

    /**
     * 删除指定id的产品库
     * 
     * @param input
     * @return
     */
    public int delProLib(Map<String, String> input);

    /**
     * 添加产品
     * 
     * @param input
     * @return
     */
    public long addProLib(Map<String, String> input);

    /**
     * 获取产品图片
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getProLibPic(String id);

    public int addProLibPic(Map input);

    public ProLib getProLibById(String id);

    public List<LinkManEntity> getLinkMan(Map<String, String> param);

    public String getLinkManCount(Map<String, String> param);

    public long addLinkMan(Map<String, String> input);

    public int delLinkMan(Map<String, String> input);

    public int updateLinkMan(Map<String, String> input);

    public List<ProNameEntity> getProNames(Map<String, String> input);

}
