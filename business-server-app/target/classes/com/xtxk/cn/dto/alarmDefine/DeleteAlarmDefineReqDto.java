package com.xtxk.cn.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class DeleteAlarmDefineReqDto {

    /**
     * 告警id
     */
    @ApiModelProperty(value = "告警id", required = true)
    @NotNull(message = "告警id不能为空")
    private Integer[] ids;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }
}
