package com.xtxk.cn.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AlgorithmicModelEnum {
//    1:;机动车不礼让行人检测;2:机动车乱停乱放检测3:主干道遛狗检测;4:遛狗未栓狗绳检测;5:高空抛物检测;6:围栏翻越检测;7:围栏隔墙递物检测
    JDCBLRXRJC(1,"机动车不礼让行人检测"),
    JDCLTLFJC(2,"机动车乱停乱放检测"),
    ZGDLGJC(3,"主干道遛狗检测"),
    LGWSGSJC(4,"遛狗未栓狗绳检测"),
    GKPWJC(5,"高空抛物检测"),
    WLFYJC(6,"围栏翻越检测"),
    WLGQDWJC(7,"围栏隔墙递物检测");
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

    AlgorithmicModelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String queryDescByCode(String code){
        Optional<AlgorithmicModelEnum> first = Arrays.stream(AlgorithmicModelEnum.values()).filter(temp -> temp.code.equals(code)).findFirst();
        if (first.isPresent()) {
            return first.get().desc;
        }
        return null;
    }
}
