package com.xtxk.cn.dto.personInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonTypeRspDto {

    @ApiModelProperty("用户类型具体看字典配置")
    private String personType;

    @ApiModelProperty("数量")
    private Integer cnt;


    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}