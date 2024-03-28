package com.xtxk.cn.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * HousingEstateSitDto
 *
 * @author chenzhi
 * @date 2022/10/19 11:09
 * @description
 */
@ApiModel(description = "小区概况Dto")
public class HousingEstateSitDto {

    @ApiModelProperty(value = "小区面积统计")
    private HousingEstateAreaPO housingEstateAreaPO;

    @ApiModelProperty(value = "人车设备统计")
    private PerCarDevCount perCarDevCount;

    @ApiModelProperty(value = "楼栋数量与户数")
    private BuildingCntDto buildingCntDto;

    public HousingEstateAreaPO getHousingEstateAreaPO() {
        return housingEstateAreaPO;
    }

    public void setHousingEstateAreaPO(HousingEstateAreaPO housingEstateAreaPO) {
        this.housingEstateAreaPO = housingEstateAreaPO;
    }

    public PerCarDevCount getPerCarDevCount() {
        return perCarDevCount;
    }

    public void setPerCarDevCount(PerCarDevCount perCarDevCount) {
        this.perCarDevCount = perCarDevCount;
    }

    public BuildingCntDto getBuildingCntDto() {
        return buildingCntDto;
    }

    public void setBuildingCntDto(BuildingCntDto buildingCntDto) {
        this.buildingCntDto = buildingCntDto;
    }
}
