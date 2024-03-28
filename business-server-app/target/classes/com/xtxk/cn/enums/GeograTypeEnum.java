package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * GeograTypeEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:05
 * @description
 */
public enum GeograTypeEnum {

    DEVICE(1, "监控设备"),

    GATEMACHINE(2, "门禁闸机");



    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    GeograTypeEnum(Integer code, String desc) {
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
        Optional<GeograTypeEnum> first = Arrays.stream(GeograTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<GeograTypeEnum> first = Arrays.stream(GeograTypeEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
