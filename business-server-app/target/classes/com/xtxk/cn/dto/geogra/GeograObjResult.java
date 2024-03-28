package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * GeograObjResult
 *
 * @author chenzhi
 * @date 2022/10/27 10:19
 * @description
 */
@ApiModel(description = "地理信息结果")
public class GeograObjResult {

    @ApiModelProperty(value = "地理信息对象集合")
    private List<GeograObjDto> resources;

    public List<GeograObjDto> getResources() {
        return resources;
    }

    public void setResources(List<GeograObjDto> resources) {
        this.resources = resources;
    }
}
