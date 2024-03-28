package com.xtxk.cn.third.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 10:48
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hk")
public class HkConfiguration implements Serializable {

    private static final long serialVersionUID = 5006599203027014036L;

    private String host;
    private String appKey;
    private String appSecret;
    /***
     * 业务相关
     */
    private String pushMessageUrl;
    private String eventCode;

    private final String artemis_path = "/artemis";


    /***
     * TODO 获取hk token,无请求参数,token有效期12h
     * @return
     */
    public String getTokenUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(artemis_path)
                .append("/api/v1/oauth/token");
        return builder.toString();
    }

    /***
     * 告警事件订阅
     * @return
     */
    public String getEventSubUrl(){
        StringBuilder builder = new StringBuilder();
        builder.append(artemis_path)
                .append("/api/eventService/v1/eventSubscriptionByEventTypes");
        return builder.toString();
    }

    /***
     * 查询事件订阅接口
     * @return
     */
    public String getEventSubViewUrl(){
        StringBuilder builder = new StringBuilder();
        builder.append(artemis_path)
                .append("/api/eventService/v1/eventSubscriptionView");
        return builder.toString();
    }

    /***
     * 查询事件订阅接口
     * @return
     */
    public String getStopEventSubUrl(){
        StringBuilder builder = new StringBuilder();
        builder.append(artemis_path)
                .append("/api/eventService/v1/eventUnSubscriptionByEventTypes");
        return builder.toString();
    }

}
