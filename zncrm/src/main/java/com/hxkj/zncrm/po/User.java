package com.hxkj.zncrm.po;

public class User {

    private String username;

    private String password;

    private String role;

    private String picId;

    private String real_name;

    public String getReal_name() {

        return real_name;
    }

    public void setReal_name(String real_name) {

        this.real_name = real_name;
    }

    public String getPicId() {

        return picId;
    }

    public void setPicId(String picId) {

        this.picId = picId;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {

        this.role = role;
    }

}
