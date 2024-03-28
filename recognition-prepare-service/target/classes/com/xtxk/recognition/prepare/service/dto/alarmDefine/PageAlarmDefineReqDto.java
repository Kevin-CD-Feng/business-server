package com.xtxk.recognition.prepare.service.dto.alarmDefine;

import com.xtxk.recognition.prepare.service.common.PageReqDto;
import io.swagger.annotations.ApiModelProperty;

public class PageAlarmDefineReqDto extends PageReqDto {

    @ApiModelProperty(value = "事件名称")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
