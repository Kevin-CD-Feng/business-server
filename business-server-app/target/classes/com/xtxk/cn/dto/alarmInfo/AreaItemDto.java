package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

public class AreaItemDto {
    @ApiModelProperty(value = "区域编号", required = true)
    private String areaCode;

    @ApiModelProperty(value = "区域名称", required = true)
    private String areaName;

    @ApiModelProperty(value = "区域数量", required = true)
    private int areaCount;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(int areaCount) {
        this.areaCount = areaCount;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
