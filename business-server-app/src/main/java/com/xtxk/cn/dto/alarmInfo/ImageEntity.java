package com.xtxk.cn.dto.alarmInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImageEntity {
    @ApiModelProperty(name = "title",value = "标题",required = true)
    private String title;
    @ApiModelProperty(name = "content",value = "图片二进制",required = true)
    private String content;
}