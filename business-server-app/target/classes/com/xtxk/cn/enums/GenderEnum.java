package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * GenderEnum
 *
 * @author chenzhi
 * @date 2022/10/14 9:51
 * @description
 */
public enum GenderEnum {
    MALE(1, "男"),

    FEMALE(2, "女");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    GenderEnum(Integer code, String desc) {
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
        Optional<GenderEnum> first = Arrays.stream(GenderEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }
}
