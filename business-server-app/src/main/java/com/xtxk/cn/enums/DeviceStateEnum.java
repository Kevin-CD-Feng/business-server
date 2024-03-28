package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * DeviceStateEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:05
 * @description
 */
public enum DeviceStateEnum {

    ONLINE(0, "在线"),

    OFFLINE(1, "离线");


    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    DeviceStateEnum(Integer code, String desc) {
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
        Optional<DeviceStateEnum> first = Arrays.stream(DeviceStateEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<DeviceStateEnum> first = Arrays.stream(DeviceStateEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
