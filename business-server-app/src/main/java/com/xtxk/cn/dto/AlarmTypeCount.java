package com.xtxk.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * AlarmTypeCount
 *
 * @author chenzhi
 * @date 2022/10/25 9:23
 * @description
 */
@ApiModel(description = "告警类型数量结果")
public class AlarmTypeCount {

    @ApiModelProperty(value = "告警类型", example = "1")
    private String alramTypeCode;

    @ApiModelProperty(value = "告警数量", example = "100")
    private Integer alarmCount;

    @ApiModelProperty(value = "告警类型中文描述", example = "高空抛物监控")
    private String alarmTypeDesc;

    public String getAlramTypeCode() {
        return alramTypeCode;
    }

    public void setAlramTypeCode(String alramTypeCode) {
        this.alramTypeCode = alramTypeCode;
    }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getAlarmTypeDesc() {
        return alarmTypeDesc;
    }

    public void setAlarmTypeDesc(String alarmTypeDesc) {
        this.alarmTypeDesc = alarmTypeDesc;
    }
}
