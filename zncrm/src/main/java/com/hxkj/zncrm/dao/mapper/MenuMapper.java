package com.hxkj.zncrm.dao.mapper;

import java.util.List;
import java.util.Map;

import com.hxkj.zncrm.dao.domain.Menu;

public interface MenuMapper {

    public List<Menu> getMeunByUsername(Map<String, String> input);
}
