package com.xtxk.cn.dto.personInfo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@ApiModel
public class DateBinderBo {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "beginDate",value = "开始时间",required = true)
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(name = "endDate",value = "结束时间",required = true)
    private Date endDate;
}