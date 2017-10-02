package com.hxkj.zncrm.dao.domain;

public class FileEntity {

    private long file_id;

    private String menu_id;

    private String title;

    private String author;

    private String file_name;

    private String last_modify;

    private String status;

    public long getFile_id() {

        return file_id;
    }

    public void setFile_id(long file_id) {

        this.file_id = file_id;
    }

    public String getMenu_id() {

        return menu_id;
    }

    public void setMenu_id(String menu_id) {

        this.menu_id = menu_id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getFile_name() {

        return file_name;
    }

    public void setFile_name(String file_name) {

        this.file_name = file_name;
    }

    public String getLast_modify() {

        return last_modify;
    }

    public void setLast_modify(String last_modify) {

        this.last_modify = last_modify;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

}
