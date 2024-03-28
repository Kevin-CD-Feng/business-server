package com.xtxk.recognition.prepare.service.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class QueryAlgorithmicBindingDetailRespDto {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "告警事件code", required = true)
    @NotNull(message = "告警事件code不能为空")
    private String eventCode;

    @ApiModelProperty(value = "算法名称code", required = true)
    @NotNull(message = "算法名称code不能为空")
    private String algorithmicNameCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
