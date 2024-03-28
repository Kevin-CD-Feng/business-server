package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

public class FlashingRespDto {

    @ApiModelProperty(value = "数量", required = true)
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
