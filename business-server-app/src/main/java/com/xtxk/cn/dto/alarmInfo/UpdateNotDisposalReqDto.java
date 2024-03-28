package com.xtxk.cn.dto.alarmInfo;

import com.xtxk.cn.utils.valid.ListValues;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UpdateNotDisposalReqDto {

    @ApiModelProperty(value = "告警id",required = true)
    private Integer id;

    @ApiModelProperty(value = "是否需实时处置(0:非实时处理；1：实时处理)",hidden = true)
    //@ListValues(arrayValue = {0,1}, message = "实时处置参数错误")
    private Integer processFlag;

    @ApiModelProperty(value = "事件描述",required = true)
    private String eventDesc;

    @ApiModelProperty(value = "处理人员",hidden = true)
    private List<PropertyPersonItemDto> dealPerson;

    @ApiModelProperty(value = "处理类型(0:任务下发；1：事件留存)",hidden = true)
    //@ListValues(arrayValue = {0,1}, message = "处理类型参数错误")
    private Integer dealType;

    @ApiModelProperty(value = "处理措施(2:待处置；3：处置中;4:已处置)")
    @ListValues(arrayValue = {2,3,4}, message = "处理类型参数错误")
    private Integer dealStatus;

    @ApiModelProperty(value = "处理人员，dealPerson废弃")
    private String userId;


    @ApiModelProperty(value = "处理人员姓名，dealPerson废弃")
    private String userName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(Integer processFlag) {
        this.processFlag = processFlag;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public List<PropertyPersonItemDto> getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(List<PropertyPersonItemDto> dealPerson) {
        this.dealPerson = dealPerson;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
