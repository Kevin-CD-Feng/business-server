package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * TimeQryTypeEnum
 *
 * @author chenzhi
 * @date 2022/10/14 9:57
 * @description
 */
public enum TimeQryTypeEnum {

    SAME_DAY(1, "当天"),

    SAME_MONTH(2, "当月"),

    SAME_YEAR(3, "当年"),

    SAME_ALL(0, "全部");

    /**
     * code
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    TimeQryTypeEnum(Integer code, String desc) {
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
        Optional<TimeQryTypeEnum> first = Arrays.stream(TimeQryTypeEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }
}
