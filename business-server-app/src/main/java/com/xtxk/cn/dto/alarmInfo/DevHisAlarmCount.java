package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DevHisAlarmCount
 *
 * @author chenzhi
 * @date 2023/1/7 9:53
 * @description
 */
@ApiModel(description = "告警详情返回")
public class DevHisAlarmCount {

    @ApiModelProperty(value = "设备id", example = "2cea6e9dbde840b885d197837387a0c7")
    private String resourceId;

    @ApiModelProperty(value = "历史告警总数", example = "290")
    private Long historyAlarmCount;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Long getHistoryAlarmCount() {
        return historyAlarmCount;
    }

    public void setHistoryAlarmCount(Long historyAlarmCount) {
        this.historyAlarmCount = historyAlarmCount;
    }
}
