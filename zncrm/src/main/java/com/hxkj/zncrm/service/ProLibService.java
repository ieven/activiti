package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.LinkManEntity;
import com.hxkj.zncrm.dao.domain.ProLib;
import com.hxkj.zncrm.po.ProNameEntity;

public interface ProLibService {

    @SuppressWarnings("rawtypes")
    public List<ProLib> getProLib(Map input);

    @SuppressWarnings("rawtypes")
    public String getProLibCount(Map input);

    @SuppressWarnings("rawtypes")
    public int updateProLib(Map input);

    @SuppressWarnings("rawtypes")
    public int delProLib(Map input);

    @SuppressWarnings("rawtypes")
    public long addProLib(Map input);

    public byte[] getProLibPic(String id);

    public int addProLibPic(String proId, byte[] bs);

    public ProLib getProLibById(String proId);

    public List<LinkManEntity> getLinkMan(Map<String, String> input);

    public String getLinkManCount(Map<String, String> input);

    public long addLinkMan(Map<String, String> input);

    public int delLinkMan(Map<String, String> input);

    public int updateLinkMan(Map<String, String> input);

    public List<ProNameEntity> getProNames(Map<String, String> input);

}
