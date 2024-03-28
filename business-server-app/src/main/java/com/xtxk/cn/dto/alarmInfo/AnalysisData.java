package com.xtxk.cn.dto.alarmInfo;

public class AnalysisData {

    private Integer userId;

    private Float face_conf;

    private Integer[] face_bbox;

    private Integer[] person_bbox;

    private Float person_conf;

    private Integer[] bbox;

    private Float conf;

    /**
     * 高空抛物
     */
    private Integer[][] bboxes;
    /**
     * 高空抛物
     */
    private Long[] timestamps;

    public Float getFace_conf() {
        return face_conf;
    }

    public void setFace_conf(Float face_conf) {
        this.face_conf = face_conf;
    }

    public Integer[] getFace_bbox() {
        return face_bbox;
    }

    public void setFace_bbox(Integer[] face_bbox) {
        this.face_bbox = face_bbox;
    }

    public Integer[] getPerson_bbox() {
        return person_bbox;
    }

    public void setPerson_bbox(Integer[] person_bbox) {
        this.person_bbox = person_bbox;
    }

    public Float getPerson_conf() {
        return person_conf;
    }

    public void setPerson_conf(Float person_conf) {
        this.person_conf = person_conf;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer[] getBbox() {
        return bbox;
    }

    public void setBbox(Integer[] bbox) {
        this.bbox = bbox;
    }

    public Float getConf() {
        return conf;
    }

    public void setConf(Float conf) {
        this.conf = conf;
    }

    public Integer[][] getBboxes() {
        return bboxes;
    }

    public void setBboxes(Integer[][] bboxes) {
        this.bboxes = bboxes;
    }

    public Long[] getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Long[] timestamps) {
        this.timestamps = timestamps;
    }
}
