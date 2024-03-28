package com.xtxk.cn.dto.AlarmPolicyConfiguration;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class WebCoordinateDto {

    @ApiModelProperty(value = "区域类型，1：矩形", required = true)
    private Integer areaType;

    @ApiModelProperty(value = "roi框左上角点x坐标", required = true)
    private Integer realX;

    @ApiModelProperty(value = "roi框左上角点y坐标", required = true)
    private Integer realY;

    @ApiModelProperty(value = "roi框宽度", required = true)
    private Integer width;

    @ApiModelProperty(value = "roi框高度", required = true)
    private Integer height;

    @ApiModelProperty(value = "视频宽度", required = true)
    private Integer vedioWidth = 640;

    @ApiModelProperty(value = "视频高度", required = true)
    private Integer vedioHeight = 360;

    @ApiModelProperty(value = "视频宽高比例", required = true)
    private BigDecimal wideHighPercentage;

    private List<List<BigDecimal>> point;
    public List<List<BigDecimal>> getPoint() {
        return point;
    }

    public void setPoint(List<List<BigDecimal>> point) {
        this.point = point;
    }

    /**
     * 默认 线宽度
     */
    @ApiModelProperty(value = "线宽度", required = true)
    private Integer lineWidth = 2;

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public Integer getRealX() {
        return realX;
    }

    public void setRealX(Integer realX) {
        this.realX = realX;
    }

    public Integer getRealY() {
        return realY;
    }

    public void setRealY(Integer realY) {
        this.realY = realY;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getVedioWidth() {
        return vedioWidth;
    }

    public void setVedioWidth(Integer vedioWidth) {
        this.vedioWidth = vedioWidth;
    }

    public Integer getVedioHeight() {
        return vedioHeight;
    }

    public void setVedioHeight(Integer vedioHeight) {
        this.vedioHeight = vedioHeight;
    }

    public BigDecimal getWideHighPercentage() {
        return wideHighPercentage;
    }

    public void setWideHighPercentage(BigDecimal wideHighPercentage) {
        this.wideHighPercentage = wideHighPercentage;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }


}
