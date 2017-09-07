package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.ProLib;

public interface ProLibService {

    public List<ProLib> getProLib(Map input);

    public String getProLibCount(Map input);
}
