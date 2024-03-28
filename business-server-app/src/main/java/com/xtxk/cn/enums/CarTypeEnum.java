package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * CarTypeEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:05
 * @description
 */
public enum CarTypeEnum {

    LOCAL_CAR(1, "本地车辆"),

    OUTSIDE_CAR(2, "外来车辆"),

    FOCUS_CAR(3, "重点车辆");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    CarTypeEnum(Integer code, String desc) {
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
        Optional<CarTypeEnum> first = Arrays.stream(CarTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }
}
