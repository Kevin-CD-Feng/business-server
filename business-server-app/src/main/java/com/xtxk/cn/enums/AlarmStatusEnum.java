package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AlarmStatusEnum {

    //事件状态（1:未核实;2:未处置;3:处置中;4:已处置）
    WHS(1,"未核实"),
    WCZ(2,"未处置"),
    CZZ(3,"处置中"),
    YCZ(4,"已处置");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

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

    AlarmStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String queryDescByCode(Integer code){
        Optional<AlarmStatusEnum> first = Arrays.stream(AlarmStatusEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<AlarmStatusEnum> first = Arrays.stream(AlarmStatusEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
