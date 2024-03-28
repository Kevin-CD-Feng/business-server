package com.xtxk.cn.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class QueryAlarmEventReqDto {

    /**
     * 告警类型
     */
    @ApiModelProperty(value = "告警类型", required = true)
    @NotBlank(message = "告警类型不能为空")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
