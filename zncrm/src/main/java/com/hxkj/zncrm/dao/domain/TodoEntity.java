package com.hxkj.zncrm.dao.domain;

public class TodoEntity {

    private String id;
    private String initiator;
    private String executor;

    private String job_content;
    private String cur_status;
    private String note;

    public String getNote() {

        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }

    private String create_time;
    private String last_modify;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getInitiator() {

        return initiator;
    }

    public void setInitiator(String initiator) {

        this.initiator = initiator;
    }

    public String getExecutor() {

        return executor;
    }

    public void setExecutor(String executor) {

        this.executor = executor;
    }

    public String getJob_content() {

        return job_content;
    }

    public void setJob_content(String job_content) {

        this.job_content = job_content;
    }

    public String getCur_status() {

        return cur_status;
    }

    public void setCur_status(String cur_status) {

        this.cur_status = cur_status;
    }

    public String getCreate_time() {

        return create_time;
    }

    public void setCreate_time(String create_time) {

        this.create_time = create_time;
    }

    public String getLast_modify() {

        return last_modify;
    }

    public void setLast_modify(String last_modify) {

        this.last_modify = last_modify;
    }

}
