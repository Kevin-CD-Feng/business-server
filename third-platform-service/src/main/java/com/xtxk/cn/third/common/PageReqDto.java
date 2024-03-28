package com.xtxk.cn.third.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lost
 * @description PageReqDto分页请求
 * @date 2022-10-9 9:10:34
 */
@Data
public class PageReqDto implements Serializable {

    /**
     * 页号
     */
    @ApiModelProperty(value = "页号")
    private int pageNo = 1;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private int pageSize = 10;

    private Integer total;


    public PageReqDto() {
    }

    public PageReqDto(int pageNo, int pageSize, Integer total) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    public boolean isPage() {
        return total != null && total > 0 && pageNo > 0;
    }
}