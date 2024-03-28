package com.xtxk.cn.dto.alarmInfo;

import com.xtxk.cn.common.PageParam;
import com.xtxk.cn.utils.valid.ListValues;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "分页请求数据")
public class PageListReqDto extends PageParam {
    @ApiModelProperty(value = "关键字")
    private String keyWord;

    @ApiModelProperty(value = "告警事件（-1:全部，1:机动车不礼让行人;2:机动车乱停乱放;3:主干道遛狗;4:遛狗未栓狗绳;5:高空抛物;6:围栏翻越;7:围栏隔墙递物）", example = "1",required = true)
    @NotBlank
    private String event;

    @ApiModelProperty(value = "事件状态（1:未核实;2:未处置;3:处置中;4:已处置）", example = "1",required = true)
    @ListValues(arrayValue = {1,2,3,4}, message = "事件状态参数错误")
    @NotNull
    private Integer status;

    @ApiModelProperty(value = "是否需实时处置(-1:全部，0:非实时处理；1：实时处理)）", example = "1",required = true)
    @ListValues(arrayValue = {-1,0,1}, message = "告警状态参数错误")
    @NotNull
    private Integer processFlag;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(Integer processFlag) {
        this.processFlag = processFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
