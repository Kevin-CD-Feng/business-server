package com.xtxk.cn.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;


/**
 * @author Administrator
 */
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = 8898360927145265716L;
    /**
     * 页号
     */
    @ApiModelProperty(value = "页号")
    private int pageNo;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private int pageSize;

    @ApiModelProperty(value = "条件")
    private String keyWord;

}