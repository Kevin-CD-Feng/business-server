package com.xtxk.cn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * HousingEstateAreaPO
 *
 * @author chenzhi
 * @date 2022/10/19 10:10
 * @description
 */
@ApiModel(description = "小区面积PO")
public class HousingEstateAreaPO {

    @ApiModelProperty(value = "主键id",example = "1")
    private Integer housingEstateAreaId;

    @ApiModelProperty(value = "小区总面积",example = "150")
    private Integer totalArea;

    @ApiModelProperty(value = "住宅面积",example = "50")
    private Integer residenceArea;

    @ApiModelProperty(value = "办公面积",example = "100")
    private Integer officeArea;

    public Integer getHousingEstateAreaId() {
        return housingEstateAreaId;
    }

    public void setHousingEstateAreaId(Integer housingEstateAreaId) {
        this.housingEstateAreaId = housingEstateAreaId;
    }

    public Integer getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Integer totalArea) {
        this.totalArea = totalArea;
    }

    public Integer getResidenceArea() {
        return residenceArea;
    }

    public void setResidenceArea(Integer residenceArea) {
        this.residenceArea = residenceArea;
    }

    public Integer getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(Integer officeArea) {
        this.officeArea = officeArea;
    }
}
