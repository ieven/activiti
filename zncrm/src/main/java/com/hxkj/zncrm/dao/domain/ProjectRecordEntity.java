package com.hxkj.zncrm.dao.domain;

public class ProjectRecordEntity {

    private String id;
    private String project_id;

    private String info;
    private String recorder;
    private String record_time;
    private String project_status;

    public String getProject_status() {

        return project_status;
    }

    public void setProject_status(String project_status) {

        this.project_status = project_status;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getProject_id() {

        return project_id;
    }

    public void setProject_id(String project_id) {

        this.project_id = project_id;
    }

    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {

        this.info = info;
    }

    public String getRecorder() {

        return recorder;
    }

    public void setRecorder(String recorder) {

        this.recorder = recorder;
    }

    public String getRecord_time() {

        return record_time;
    }

    public void setRecord_time(String record_time) {

        this.record_time = record_time;
    }

}
