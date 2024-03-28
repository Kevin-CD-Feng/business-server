package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AlarmEventEnum {

    JDCBLRXR(1,"机动车不礼让行人"),

    JDCLTLF(2, "机动车乱停乱放"),

    ZGDLG(3,"主干道遛狗"),

    LGWSGS(4, "遛狗未栓狗绳"),

    GKPW(5,"高空抛物"),

    WLFY(6, "围栏翻越"),

    WLGQDW(7, "围栏隔墙递物");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    AlarmEventEnum(Integer code, String desc) {
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
        Optional<AlarmEventEnum> first = Arrays.stream(AlarmEventEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<AlarmEventEnum> first = Arrays.stream(AlarmEventEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
