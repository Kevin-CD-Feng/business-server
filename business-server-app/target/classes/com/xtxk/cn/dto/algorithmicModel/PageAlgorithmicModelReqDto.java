package com.xtxk.cn.dto.algorithmicModel;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

public class PageAlgorithmicModelReqDto extends PageParam {

    @ApiModelProperty(value = "算法名称")
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
