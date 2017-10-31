package com.hxkj.zncrm.service;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.Menu;

public interface MenuService {

    public List<Menu> getMeunByUsername(Map<String, String> input);

    public List<Menu> getMeunByParentId(Map<String, String> input);

    public String getMenuCount(Map<String, String> input);

    public long addMenu(Map<String, String> input);

    public int delMenu(Map<String, String> input);

    public int updateMenu(Map<String, String> input);

    public List<String> getMeunListByRole(Map<String, String> input);

}
