package com.xtxk.recognition.prepare.service.dto.alarmDefine;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PushAlarmInfoReqDto {
    @ApiModelProperty(value = "设备资源id")
    @NotBlank(message = "resourceId may not be empty.")
    private String resourceId;

    @ApiModelProperty(value = "事件类型编码")
    @NotBlank(message = "eventCode may not be empty.")
    private String eventCode;

    private Integer[][] roi;

    @ApiModelProperty(value = "抓拍时间")
    @NotBlank
    private String catchTime;

    @ApiModelProperty(value = "分析数据")
    @NotBlank(message = "data may not be empty.")
    private String data;

    @ApiModelProperty(value = "图片路径")
    @NotBlank(message = "path may not be empty.")
    private String path;

    @ApiModelProperty(value = "特征值")
    private List<String> coff;
}