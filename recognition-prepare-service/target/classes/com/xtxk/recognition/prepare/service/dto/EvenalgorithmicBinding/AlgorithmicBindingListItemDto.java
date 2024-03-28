package com.xtxk.recognition.prepare.service.dto.EvenalgorithmicBinding;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class AlgorithmicBindingListItemDto {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty(value = "告警事件", required = true)
    private String eventName;

    @ApiModelProperty(value = "算法名称", required = true)
    private String algorithmicName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAlgorithmicName() {
        return algorithmicName;
    }

    public void setAlgorithmicName(String algorithmicName) {
        this.algorithmicName = algorithmicName;
    }
}
