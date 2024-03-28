package com.xtxk.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * AlarmStatusCount
 *
 * @author chenzhi
 * @date 2022/10/20 13:48
 * @description
 */
@ApiModel(description = "告警任务处置结果")
public class AlarmStatusCount {

    @ApiModelProperty(value = "处置状态",example = "")
    private Integer status;

    @ApiModelProperty(value = "处置状态中文",example = "")
    private String statusStr;

    @ApiModelProperty(value = "告警数量",example = "")
    private Integer count;

    @ApiModelProperty(value = "百分比",example = "")
    private String percent;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
