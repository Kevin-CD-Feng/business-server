package com.xtxk.cn.enums;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 14:17
 */
public enum StatusEnum {

    SUCCESS( "success"),FAIL("fail");
    private String code;


    StatusEnum(String success) {
        this.code = success;
    }

    public String getCode() {
        return code;
    }
}
