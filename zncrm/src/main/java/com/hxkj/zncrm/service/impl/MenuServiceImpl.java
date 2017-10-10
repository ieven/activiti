package com.hxkj.zncrm.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxkj.zncrm.dao.domain.Menu;
import com.hxkj.zncrm.dao.mapper.MenuMapper;
import com.hxkj.zncrm.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper mapper;

    @Override
    public List<Menu> getMeunByUsername(Map<String, String> input) {

        List<Menu> list = mapper.getMeunByUsername(input);
        return sortMenuList(list);
    }

    private List<Menu> sortMenuList(List<Menu> list) {

        Map<String, Menu> temp = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParent_id().equals("0")) {
                temp.put(list.get(i).getId(), list.get(i));
                list.remove(i);
                i--;
            }
        }
        while (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Menu menu = list.get(i);
                if (temp.containsKey(menu.getParent_id())) {
                    temp.get(menu.getParent_id()).getChildren().add(menu);
                    list.remove(i);
                    i--;
                }
            }
        }
        List<Menu> result = new LinkedList<>();
        result.addAll(temp.values());
        return result;
    }

    @Override
    public List<Menu> getMeunByParentId(Map<String, String> input) {

        return mapper.getMeunByParentId(input);
    }

    @Override
    public String getMenuCount(Map<String, String> input) {

        return mapper.getMenuCount(input);
    }

    @Override
    public long addMenu(Map<String, String> input) {

        Map<String, Long> map = new HashMap<>(1);
        long id = mapper.addMenu(input);
        mapper.updateMenuOrder(input);
        return id;
    }

    @Override
    public int delMenu(Map<String, String> input) {

        return mapper.delMenu(input);
    }

    @Override
    public int updateMenu(Map<String, String> input) {

        return mapper.updateMenu(input);
    }

}
