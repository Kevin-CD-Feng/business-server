package com.xtxk.recognition.prepare.service.enums;

public enum FileDistPathType {
    /**
     * 任务中心上传的图片
     */
    TASK("task"),

    /**
     * 事件上报上传的图片
     */
    EVENT("event");

    public static final String PREFIX_PATH_PC = "pc";

    private String path;

    FileDistPathType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
