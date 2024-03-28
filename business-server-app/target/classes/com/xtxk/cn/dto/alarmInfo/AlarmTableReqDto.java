package com.xtxk.cn.dto.alarmInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AlarmTableReqDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
   // @NotNull(message = "开始时间不能够为空.")
    @ApiModelProperty(name = "beginDate",value = "开始时间")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
   // @NotNull(message = "结束时间不能够为空.")
    @ApiModelProperty(name = "endDate",value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "上报类型（空表表示全部 ,device_report：设备上报，person_report:人工上报）")
    private String reportType;
}