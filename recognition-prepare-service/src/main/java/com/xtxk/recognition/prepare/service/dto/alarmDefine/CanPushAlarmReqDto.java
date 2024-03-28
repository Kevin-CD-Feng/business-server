package com.xtxk.recognition.prepare.service.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CanPushAlarmReqDto {
    @ApiModelProperty(value = "事件类型编码")
    @NotBlank
    private String eventCode;

    @ApiModelProperty(value = "分析数据")
    @NotBlank
    private String data;

    @ApiModelProperty(value = "资源id")
    @NotBlank
    private String resourceId;

    @ApiModelProperty(value = "资源id")
    @NotBlank
    private String algCode;

    @ApiModelProperty(value = "特征值")
    private List<String> features;
}