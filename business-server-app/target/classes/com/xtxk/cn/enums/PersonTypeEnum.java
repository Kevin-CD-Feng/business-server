package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * PersonTypeEnum
 *
 * @author chenzhi
 * @date 2022/10/14 9:57
 * @description
 */
public enum PersonTypeEnum {

    LOCAL_PERSON(1, "本地人员"),

    OUTSIDE_PERSON(2, "外来人员"),

    MANAGER_PERSON(3, "物业人员");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    PersonTypeEnum(Integer code, String desc) {
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
        Optional<PersonTypeEnum> first = Arrays.stream(PersonTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }
}
