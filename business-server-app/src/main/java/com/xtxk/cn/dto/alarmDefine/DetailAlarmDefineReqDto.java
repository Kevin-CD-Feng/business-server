package com.xtxk.cn.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DetailAlarmDefineReqDto {

    /**
     * 告警id
     */
    @ApiModelProperty(value = "告警id", required = true)
    @NotNull(message = "告警id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
