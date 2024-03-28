package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AlarmTypeEnum {

    JDCBLRXR(1, "机动车不礼让行人"),

    JDCLTLF(2, "机动车乱停乱放"),

    BWMYQ(3, "不文明养犬"),

    GKPW(4, "高空抛物"),

    ZJRQ(5, "周界入侵");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    AlarmTypeEnum(Integer code, String desc) {
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
        Optional<AlarmTypeEnum> first = Arrays.stream(AlarmTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<AlarmTypeEnum> first = Arrays.stream(AlarmTypeEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
