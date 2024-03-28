package com.xtxk.cn.service.impl.statusmonitorweb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description AgentProperty
 * @Date create in 2022/9/6 17:20
 */
@Component
@ConfigurationProperties(prefix = "status-monitor-web")
public class StatusMonitorWebProperty {

    private String url;

    private String token;

    private String departmentId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
