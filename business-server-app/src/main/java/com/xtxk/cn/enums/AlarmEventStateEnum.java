package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * AlarmEventStateEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:05
 * @description
 */
public enum AlarmEventStateEnum {
    UNCHECKED(1, "未核实"),
    UNDEALED(2, "未处置"),
    DEALING(3, "处置中"),
    DEALED(4, "已处置");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    AlarmEventStateEnum(Integer code, String desc) {
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

    public static String queryDescByCode(Integer code) {
        Optional<AlarmEventStateEnum> first = Arrays.stream(AlarmEventStateEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc) {
        Optional<AlarmEventStateEnum> first = Arrays.stream(AlarmEventStateEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
