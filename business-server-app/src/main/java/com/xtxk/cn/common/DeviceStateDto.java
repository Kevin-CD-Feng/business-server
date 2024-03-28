package com.xtxk.cn.common;

import java.math.BigDecimal;
import java.util.List;

/**
 * DeviceStateDto
 *
 * @author chenzhi
 * @date 2022/11/2 15:57
 * @description
 */
public class DeviceStateDto {

    private String deviceType;

    private Boolean isBim;

    private String resourceSIPID;

    private String factoryInfo;

    private String phoneType;

    private String resourceID;

    private String departmentID;

    private BigDecimal latitude;

    private Integer index;

    private String resourceName;

    private String terminalType;

    private List channels;

    private String registerNodeID;

    private String dirName;

    private BigDecimal longitude;

    private String resourceType;

    /**
     * DeviceOnline DeviceOffline
     */
    private String status;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Boolean getBim() {
        return isBim;
    }

    public void setBim(Boolean bim) {
        isBim = bim;
    }

    public String getResourceSIPID() {
        return resourceSIPID;
    }

    public void setResourceSIPID(String resourceSIPID) {
        this.resourceSIPID = resourceSIPID;
    }

    public String getFactoryInfo() {
        return factoryInfo;
    }

    public void setFactoryInfo(String factoryInfo) {
        this.factoryInfo = factoryInfo;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public List getChannels() {
        return channels;
    }

    public void setChannels(List channels) {
        this.channels = channels;
    }

    public String getRegisterNodeID() {
        return registerNodeID;
    }

    public void setRegisterNodeID(String registerNodeID) {
        this.registerNodeID = registerNodeID;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
