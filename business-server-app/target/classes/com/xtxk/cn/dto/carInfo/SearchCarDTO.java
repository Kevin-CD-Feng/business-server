package com.xtxk.cn.dto.carInfo;

import com.xtxk.cn.common.PageParam;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author HW
 */
public class SearchCarDTO extends PageParam implements Serializable {

    private static final long serialVersionUID = 1948959867666661999L;

    @ApiModelProperty(value = "关键字")
    private String keyWords;

    @ApiModelProperty(value = "车辆类型")
    private String carType;


    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
