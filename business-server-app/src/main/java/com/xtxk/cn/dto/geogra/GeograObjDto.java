package com.xtxk.cn.dto.geogra;

import com.xtxk.cn.entity.AlarmInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * GeograObjDto
 *
 * @author chenzhi
 * @date 2022/10/27 10:08
 * @description
 */
@ApiModel(description = "地理信息对象")
public class GeograObjDto {

    @ApiModelProperty(value = "id", example = "0001")
    private String id;

    @ApiModelProperty(value = "名称", example = "海康威视1号")
    private String name;

    @ApiModelProperty(value = "所属区域", example = "district_one")
    private String district;

    @ApiModelProperty(value = "所属区域名称", example = "一区")
    private String districtName;

    @ApiModelProperty(value = "历史告警数量", example = "290")
    private Long historyAlarmCount;

    @ApiModelProperty(value = "类型 1：监控设备 2：告警 3：门禁闸机", example = "1")
    private String type;

    @ApiModelProperty(value = "监控设备状态 0：在线 1：离线 2：告警", example = "1")
    private String status;

    @ApiModelProperty(value = "人员位置信息", example = "./icon/人员.png")
    private String uri;

    @ApiModelProperty(value = "描述信息", example = "人员信息 \\n 位置：xxx \\n 描述：xxx")
    private String description;

    @ApiModelProperty(value = "经度", example = "114.295655")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", example = "30.5662")
    private BigDecimal latitude;

    @ApiModelProperty(value = "高度", example = "10.5")
    private Double height;

    @ApiModelProperty(value = "图标大小", example = "0.2")
    private Float scale;

    @ApiModelProperty(value = "最小高度", example = "0")
    private Float heightMin;

    @ApiModelProperty(value = "最大高度", example = "10000")
    private Float heightMax;

    @ApiModelProperty(value = "告警信息", example = "{}")
    private AlarmInfo alarmInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public Float getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Float heightMin) {
        this.heightMin = heightMin;
    }

    public Float getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Float heightMax) {
        this.heightMax = heightMax;
    }

    public AlarmInfo getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getHistoryAlarmCount() {
        return historyAlarmCount;
    }

    public void setHistoryAlarmCount(Long historyAlarmCount) {
        this.historyAlarmCount = historyAlarmCount;
    }
}
