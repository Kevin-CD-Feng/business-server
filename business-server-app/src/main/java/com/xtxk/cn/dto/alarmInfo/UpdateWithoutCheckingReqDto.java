package com.xtxk.cn.dto.alarmInfo;

import com.xtxk.cn.utils.valid.ListValues;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class UpdateWithoutCheckingReqDto {
    @ApiModelProperty(value = "告警id",required = true)
    private Integer id;

    @ApiModelProperty(value = "事件",hidden = true)
   // @NotBlank
    private String event;

    @ApiModelProperty(value = "事件状态",hidden = true)
   // @ListValues(arrayValue = {1,2,3,4}, message = "事件状态参数错误")
    private Integer status;

    @ApiModelProperty(value = "是否真实， 1：真实事件 2：误报事件",required = true)
    @ListValues(arrayValue = {1,2}, message = "是否真实参数错误")
    private Integer isReal;

    @ApiModelProperty(value = "事件描述",required = true)
    private String eventDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsReal() {
        return isReal;
    }

    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
