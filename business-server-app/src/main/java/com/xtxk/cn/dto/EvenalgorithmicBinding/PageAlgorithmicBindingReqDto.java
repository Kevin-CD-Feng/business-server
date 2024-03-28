package com.xtxk.cn.dto.EvenalgorithmicBinding;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

public class PageAlgorithmicBindingReqDto extends PageParam {

    @ApiModelProperty(value = "事件名称")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
