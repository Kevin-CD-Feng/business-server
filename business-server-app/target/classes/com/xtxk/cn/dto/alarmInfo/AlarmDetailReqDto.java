package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class AlarmDetailReqDto {

    @ApiModelProperty(value = "告警id", required = true)
    @NotNull(message = "id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
