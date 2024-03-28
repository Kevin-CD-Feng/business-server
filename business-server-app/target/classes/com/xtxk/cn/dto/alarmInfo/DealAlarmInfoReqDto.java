package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DealAlarmInfoReqDto {

    @ApiModelProperty(value = "图片base64")
    private String image;

    @ApiModelProperty(value = "设备资源id", required = true)
    @NotBlank
    private String resourceId;

    @ApiModelProperty(value = "抓拍时间", required = true)
    @NotBlank
    private String catchTime;

    @ApiModelProperty(value = "事件类型编码", required = true)
    @NotBlank
    private String eventCode;

    private Integer[][] roi;

    @ApiModelProperty(value = "分析数据")
    private String data;

    @ApiModelProperty(value = "图片路径，该路径将保存到数据库", required = true)
    private String path;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(String catchTime) {
        this.catchTime = catchTime;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }


    public Integer[][] getRoi() {
        return roi;
    }

    public void setRoi(Integer[][] roi) {
        this.roi = roi;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
