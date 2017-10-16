package com.hxkj.zncrm.po;

import java.util.List;

public class RoleAuthEntity {

    private List<String> menuList;

    private List<String> authorities;

    public List<String> getMenuList() {

        return menuList;
    }

    public void setMenuList(List<String> menuList) {

        this.menuList = menuList;
    }

    public List<String> getAuthorities() {

        return authorities;
    }

    public void setAuthorities(List<String> authorities) {

        this.authorities = authorities;
    }

}
