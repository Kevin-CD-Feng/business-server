package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AlarmResourceTypeEnum {

    DEVICE_REPORT(1, "设备上报"),

    PERSON_REPORT(2, "人工上报");



    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    AlarmResourceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String queryDescByCode(Integer code){
        Optional<AlarmResourceTypeEnum> first = Arrays.stream(AlarmResourceTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<AlarmResourceTypeEnum> first = Arrays.stream(AlarmResourceTypeEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
