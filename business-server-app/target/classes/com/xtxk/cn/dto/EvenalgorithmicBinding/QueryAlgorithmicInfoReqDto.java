package com.xtxk.cn.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class QueryAlgorithmicInfoReqDto {

    @ApiModelProperty(value = "告警事件", required = true)
    @NotNull(message = "告警事件不能为空")
    private String eventCode;

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
}
