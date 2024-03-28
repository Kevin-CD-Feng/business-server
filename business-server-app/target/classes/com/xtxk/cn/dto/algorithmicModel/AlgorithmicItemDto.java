package com.xtxk.cn.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

public class AlgorithmicItemDto {

    @ApiModelProperty(value = "算法code", required = true)
    private String algorithmCode;

    @ApiModelProperty(value = "请求参数范例", required = true)
    private String algorithmReqJsonStr;

    @ApiModelProperty(value = "算法服务地址", required = true)
    private String algorithmUrl;

    public String getAlgorithmCode() {
        return algorithmCode;
    }

    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }

    public String getAlgorithmReqJsonStr() {
        return algorithmReqJsonStr;
    }

    public void setAlgorithmReqJsonStr(String algorithmReqJsonStr) {
        this.algorithmReqJsonStr = algorithmReqJsonStr;
    }

    public String getAlgorithmUrl() {
        return algorithmUrl;
    }

    public void setAlgorithmUrl(String algorithmUrl) {
        this.algorithmUrl = algorithmUrl;
    }
}
