package com.xtxk.recognition.prepare.service.dto.algorithmicModel;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateAlgorithmicModelReqDto {

    @ApiModelProperty(value = "算法模型id", required = true)
    @NotNull(message = "算法模型id不能为空")
    private Integer id;

    @ApiModelProperty(value = "算法code", required = true)
    @NotNull(message = "算法code不能为空")
    @Size(min = 1,max = 20,message = "算法code长度1-20")
    private String nameCode;

    @ApiModelProperty(value = "算法模型url", required = true)
    @NotBlank(message = "算法模型url不能为空")
    @Size(min = 7,max = 100,message = "算法模型url长度7-100")
    private String url;

    @ApiModelProperty(value = "参数", required = true)
    @NotBlank(message = "参数不能为空")
    @Size(min = 2,max = 200,message = "算法参数长度2-200")
    private String parameterSample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameterSample() {
        return parameterSample;
    }

    public void setParameterSample(String parameterSample) {
        this.parameterSample = parameterSample;
    }
}
