package com.xtxk.cn.dto.geogra;

import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.enums.DistrictEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * MonitorDeviceDto
 *
 * @author chenzhi
 * @date 2022/10/19 14:10
 * @description
 */
@ApiModel(description = "区域监控设备列表")
public class MonitorDeviceDto {

    @ApiModelProperty( value = "区域",example = "1")
    private String district;

    @ApiModelProperty( value = "区域名称",example = "区域一")
    private String districtName;

    @ApiModelProperty( value = "监控设备信息列表")
    private List<MonitorDevicePO> deviceList;

    @ApiModelProperty( value = "监控设备数量",example = "5")
    private Integer deviceCount=0;

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

    public List<MonitorDevicePO> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<MonitorDevicePO> deviceList) {
        this.deviceList = deviceList;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }
}
