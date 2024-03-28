package com.xtxk.recognition.prepare.service.common;

import io.swagger.annotations.ApiModelProperty;

public class PageReqDto {

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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}