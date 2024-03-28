package com.xtxk.recognition.prepare.service.dto.algorithmicModel;

import com.xtxk.recognition.prepare.service.common.PageReqDto;
import io.swagger.annotations.ApiModelProperty;

public class PageAlgorithmicModelReqDto extends PageReqDto {

    @ApiModelProperty(value = "算法名称")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
