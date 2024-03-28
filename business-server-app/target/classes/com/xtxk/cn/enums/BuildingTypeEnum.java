package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * BuildingTypeEnum
 *
 * @author chenzhi
 * @date 2022/10/14 10:05
 * @description
 */
public enum BuildingTypeEnum {

    RESIDENCE_BUILDING(1, "居民楼"),
    OFFICE_BUILDING(2, "办公楼"),
    HOSPITAL(3, "医院");


    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    BuildingTypeEnum(Integer code, String desc) {
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
        Optional<BuildingTypeEnum> first = Arrays.stream(BuildingTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }

    public static Integer queryCodeByDesc(String desc){
        Optional<BuildingTypeEnum> first = Arrays.stream(BuildingTypeEnum.values()).filter(temp -> temp.desc.equals(desc)).findFirst();
        if (first.isPresent()) {
            return first.get().code;
        }
        return null;
    }
}
