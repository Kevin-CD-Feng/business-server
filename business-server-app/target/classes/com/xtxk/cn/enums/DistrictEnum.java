package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * DistrictEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:01
 * @description
 */
public enum DistrictEnum {

    DISTRICT_ONE(1, "一区"),

    DISTRICT_TWO(2, "二区"),

    DISTRICT_THREE(3, "三区"),
    DISTRICT_FOUR(4, "四区"),
    DISTRICT_FIVE(5, "五区");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    DistrictEnum(Integer code, String desc) {
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
        Optional<DistrictEnum> first = Arrays.stream(DistrictEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<DistrictEnum> first = Arrays.stream(DistrictEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
