package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum DisposalTypeEnum {

    NON_REAL_TIME(0, "非实时处理"),

    REAL_TIME(1, "实时处理");


    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    DisposalTypeEnum(Integer code, String desc) {
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
        Optional<DisposalTypeEnum> first = Arrays.stream(DisposalTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<DisposalTypeEnum> first = Arrays.stream(DisposalTypeEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
