package com.xtxk.cn.third.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;

/***
 * @description 区域湖数据配置及相关接口地址配置
 * @author liulei
 * @date 2023-08-22 10:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "datalake.config")
public class DataLakeConfiguration implements Serializable {

    private static final long serialVersionUID = -6804583881307687212L;

    private String url;
    private String identity;
    private String clientId;
    private String clientSecret;


    /**
     * 获取区域湖token地址
     */
    public String getTokenUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/uaa/oauth/token?grant_type=client_credentials&client_id=").append(getClientId())
                .append("&client_secret=")
                .append(getClientSecret());
        return builder.toString();
    }


    /***
     * 获取小区人员地址
     * @return
     */
    public String getUserUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/api/dde/shareddataservice/MS_BAS_BASIC_PERSONNEL?identity=").append(getIdentity());
        return builder.toString();
    }

    /***
     * 获取车辆信息地址
     * @return
     */
    public String getCarUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/api/dde/shareddataservice/MS_BAS_VEHICLE_INFORMATION?identity=").append(getIdentity());
        return builder.toString();
    }

    /***
     * 获取楼宇信息地址
     * @return
     */
    public String getBuildUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/api/dde/shareddataservice/MS_BAS_BUILDING_INF?identity=").append(getIdentity());
        return builder.toString();
    }

    /***
     * 获取房屋地址
     * @return
     */
    public String getHouseUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/api/dde/shareddataservice/MS_BAS_HOUSE_INF?identity=").append(getIdentity());
        return builder.toString();
    }

    /***
     * 拉取房屋住户数据
     * @return
     */
    public String getHouseHoldUrl() {
        StringBuilder builder = new StringBuilder(getUrl());
        builder.append("/api/dde/shareddataservice/MS_BAS_HOUSEHOLD_INF?identity=").append(getIdentity());
        return builder.toString();
    }




}
