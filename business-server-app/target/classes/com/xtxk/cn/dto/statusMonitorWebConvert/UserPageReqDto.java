package com.xtxk.cn.dto.statusMonitorWebConvert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 临时用户分页请求
 * @Date create in 2022/11/7 16:00
 */
@ApiModel(description = "临时用户分页请求")
public class UserPageReqDto {

    @ApiModelProperty(value = "关键字(用户账户或者用户名)", required = true)
    private String key;

    @ApiModelProperty(value = "页号", required = true)
    @NotNull(message = "页号不能为空")
    @Min(value = 1,message = "页号最小为1")
    private Integer pageNo = 1;

    @Min(1)
    @ApiModelProperty(value = "分页大小", required = true)
    @Min(value = 1,message = "分页大小最小为1")
    private Integer pageSize = 20;

    @ApiModelProperty(value = "账户类型 1:油田账户 2：临时账户", required = true)
    @NotNull(message = "账户类型不能为空")
    private Integer accountType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
