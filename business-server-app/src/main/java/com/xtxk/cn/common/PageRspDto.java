package com.xtxk.cn.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author lost
 * @description PageRspDto分页返回
 * @date 2022-10-9 9:10:34
 */
@ApiModel
public class PageRspDto<T> {
    /**
     * 总数
     */
    @ApiModelProperty(value = "总记录数")
    private long count;

    /**
     * 分页总数
     */
    @ApiModelProperty(value = "总分页数")
    private int pageCount;

    /**
     * 列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> list;

    public PageRspDto() {
    }

    public PageRspDto(long count, int pageCount, List<T> list) {
        this.count = count;
        this.pageCount = pageCount;
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}