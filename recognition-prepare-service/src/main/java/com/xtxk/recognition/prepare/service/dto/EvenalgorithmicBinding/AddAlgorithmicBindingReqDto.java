package com.xtxk.recognition.prepare.service.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddAlgorithmicBindingReqDto {

    /**
     * 告警事件
     */
    @ApiModelProperty(value = "告警事件code", required = true)
    @NotBlank(message = "告警事件code不能为空")
    private String eventCode;

    /**
     * 告警事件
     */
    @ApiModelProperty(value = "算法名称code", required = true)
    @NotNull(message = "算法名称code不能为空")
    private String algorithmicNameCode;

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getAlgorithmicNameCode() {
        return algorithmicNameCode;
    }

    public void setAlgorithmicNameCode(String algorithmicNameCode) {
        this.algorithmicNameCode = algorithmicNameCode;
    }
}
