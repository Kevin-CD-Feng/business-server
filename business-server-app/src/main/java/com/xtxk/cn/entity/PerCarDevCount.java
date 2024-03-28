package com.xtxk.cn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PerCarDevCount
 *
 * @author chenzhi
 * @date 2022/10/19 10:57
 * @description
 */
@ApiModel(description = "人车监控设备统计数量")
public class PerCarDevCount {

    @ApiModelProperty(value = "人员档案数",example = "200")
    private Integer perRecordCount;

    @ApiModelProperty(value = "车辆档案数",example = "200")
    private Integer carRecordCount;

    @ApiModelProperty(value = "监控设备总数",example = "500")
    private Integer devRecordCount;

    public Integer getPerRecordCount() {
        return perRecordCount;
    }

    public void setPerRecordCount(Integer perRecordCount) {
        this.perRecordCount = perRecordCount;
    }

    public Integer getCarRecordCount() {
        return carRecordCount;
    }

    public void setCarRecordCount(Integer carRecordCount) {
        this.carRecordCount = carRecordCount;
    }

    public Integer getDevRecordCount() {
        return devRecordCount;
    }

    public void setDevRecordCount(Integer devRecordCount) {
        this.devRecordCount = devRecordCount;
    }
}
