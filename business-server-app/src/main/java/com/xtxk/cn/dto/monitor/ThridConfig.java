package com.xtxk.cn.dto.monitor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author HW
 */

@Configuration
@ConfigurationProperties(prefix = "third")
public class ThridConfig implements Serializable {

    private static final long serialVersionUID = 1938139693991782713L;

    private String openVoneURL;

    private String operatorToken;

    private String directoryId;

    private Integer gbDevicePort;

    private Integer nonGbDevicePort;

    private String nodeId;

    private String queryTypes;

    public String getOpenVoneURL() {
        return openVoneURL;
    }

    public void setOpenVoneURL(String openVoneURL) {
        this.openVoneURL = openVoneURL;
    }

    public String getOperatorToken() {
        return operatorToken;
    }

    public void setOperatorToken(String operatorToken) {
        this.operatorToken = operatorToken;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
    }

    public String getDataRequestForWeb() {
        return new StringBuilder(getOpenVoneURL()).append("/data/dataRequestForWeb").toString();
    }

    /***
     * 平台 data/datacenter
     * @return
     */
    public String getDatacenter() {
        return new StringBuilder(getOpenVoneURL()).append("/data/datacenter").toString();
    }

    public Integer getGbDevicePort() {
        return gbDevicePort;
    }

    public void setGbDevicePort(Integer gbDevicePort) {
        this.gbDevicePort = gbDevicePort;
    }

    public Integer getNonGbDevicePort() {
        return nonGbDevicePort;
    }

    public void setNonGbDevicePort(Integer nonGbDevicePort) {
        this.nonGbDevicePort = nonGbDevicePort;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getQueryTypes() {
        return queryTypes;
    }

    public void setQueryTypes(String queryTypes) {
        this.queryTypes = queryTypes;
    }
}
