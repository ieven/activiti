package com.hxkj.zncrm.service;

import java.util.Map;

import com.hxkj.zncrm.dao.domain.Menu;

public interface MenuService {

    public Map<String, Menu> getMeunByUsername(Map<String, String> input);
}
