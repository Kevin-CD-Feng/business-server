package com.xtxk.cn.dto.monitor;

import com.xtxk.cn.enums.DeviceStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author HW
 */
public class DeviceDTO implements Serializable {

    private static final long serialVersionUID = -9104777275535460291L;

    private String deviceType;
    private Integer cmdIndex;
    private String resourceID;
    private String resourceSipId;
    private String targetNodeName;
    private String departmentID;
    private Boolean isLocalNode;
    private String ipAddress;
    private String resourceName;
    private Integer orgIndex;
    private String targetNodeId;
    private String resourceType;
    private Integer devicePort;

    private Integer deviceState;

    private String deviceStateName;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getCmdIndex() {
        return cmdIndex;
    }

    public void setCmdIndex(Integer cmdIndex) {
        this.cmdIndex = cmdIndex;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getTargetNodeName() {
        return targetNodeName;
    }

    public void setTargetNodeName(String targetNodeName) {
        this.targetNodeName = targetNodeName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public Boolean getLocalNode() {
        return isLocalNode;
    }

    public void setLocalNode(Boolean localNode) {
        isLocalNode = localNode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getOrgIndex() {
        return orgIndex;
    }

    public void setOrgIndex(Integer orgIndex) {
        this.orgIndex = orgIndex;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
    }

    public Integer getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
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

    public String getDeviceStateName() {
        return DeviceStateEnum.queryDescByCode(getDeviceState());
    }

    public void setDeviceStateName(String deviceStateName) {
        this.deviceStateName = deviceStateName;
    }

    public String getResourceSipId() {
        return resourceSipId;
    }

    public void setResourceSipId(String resourceSipId) {
        this.resourceSipId = resourceSipId;
    }
}
