package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

public class AlarmCountItemDto {
    @ApiModelProperty(value = "时间范围", required = true)
    private String time;

    @ApiModelProperty(value = "数量", required = true)
    private Integer total;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
