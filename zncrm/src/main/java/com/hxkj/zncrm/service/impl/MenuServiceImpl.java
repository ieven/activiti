package com.hxkj.zncrm.service.impl;

import java.util.HashMap;
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
    public Map<String, Menu> getMeunByUsername(Map<String, String> input) {

        List<Menu> list = mapper.getMeunByUsername(input);
        return sortMenuList(list);
    }

    private Map<String, Menu> sortMenuList(List<Menu> list) {

        Map<String, Menu> temp = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParent_id().equals("0")) {
                temp.put(list.get(i).getMenu_id(), list.get(i));
                list.remove(i);
                i--;
            }
        }
        while (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Menu menu = list.get(i);
                if (temp.containsKey(menu.getParent_id())) {
                    temp.get(menu.getParent_id()).getChildList().add(menu);
                    list.remove(i);
                    i--;
                }
            }
        }
        return temp;
    }

}
