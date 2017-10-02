package com.hxkj.zncrm.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private String id;

    private String text;
    private String parent_id;
    private String menu_level;
    private String menu_url;
    private List<Menu> children = new ArrayList<>();

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
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

    public List<Menu> getChildren() {

        return children;
    }

    public void setChildren(List<Menu> children) {

        this.children = children;
    }

}
