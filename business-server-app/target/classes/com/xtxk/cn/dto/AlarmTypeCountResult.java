package com.xtxk.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * AlarmTypeCountResult
 *
 * @author chenzhi
 * @date 2022/10/25 9:34
 * @description
 */
@ApiModel(description = "监控设备概况结果")
public class AlarmTypeCountResult {

    @ApiModelProperty(value = "设备总量", example = "50")
    private Integer deviceTotalCount;

    @ApiModelProperty(value = "告警类型对应的告警数量结果集合")
    private List<AlarmTypeCount> list;

    public Integer getDeviceTotalCount() {
        return deviceTotalCount;
    }

    public void setDeviceTotalCount(Integer deviceTotalCount) {
        this.deviceTotalCount = deviceTotalCount;
    }

    public List<AlarmTypeCount> getList() {
        return list;
    }

    public void setList(List<AlarmTypeCount> list) {
        this.list = list;
    }
}
