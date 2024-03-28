package com.xtxk.cn.third.enums.user;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-29 17:08
 */
public enum SexEnums {

    Man("男",1),WOMAN("女",2);

    private Integer code;
    private String name;


    SexEnums(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static Integer getCode(String desc) {
        for (SexEnums sex : SexEnums.values()) {
            if (sex.getName().equals(desc)) {
                return sex.getCode();
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

}
