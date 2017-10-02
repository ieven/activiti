package com.hxkj.zncrm.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private String menu_id;

    private String menu_name;
    private String parent_id;
    private String menu_level;
    private String menu_url;
    private List<Menu> childList = new ArrayList<>();

    public List<Menu> getChildList() {

        return childList;
    }

    public void setChildList(List<Menu> childList) {

        this.childList = childList;
    }

    public String getMenu_id() {

        return menu_id;
    }

    public void setMenu_id(String menu_id) {

        this.menu_id = menu_id;
    }

    public String getMenu_name() {

        return menu_name;
    }

    public void setMenu_name(String menu_name) {

        this.menu_name = menu_name;
    }

    public String getParent_id() {

        return parent_id;
    }

    public void setParent_id(String parent_id) {

        this.parent_id = parent_id;
    }

    public String getMenu_level() {

        return menu_level;
    }

    public void setMenu_level(String menu_level) {

        this.menu_level = menu_level;
    }

    public String getMenu_url() {

        return menu_url;
    }

    public void setMenu_url(String menu_url) {

        this.menu_url = menu_url;
    }

}
