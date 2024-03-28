package com.xtxk.cn.dto.geogra;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BuildingLocationInfo
 *
 * @author chenzhi
 * @date 2022/10/31 15:58
 * @description
 */
@ApiModel(description = "楼栋位置信息")
public class BuildingLocationInfo {

    private String id;

    private String name;

    private List<BigDecimal> coordinates = new ArrayList<>();

    private List<Float> vertexHeights = new ArrayList<>();

    private String buildingLongitude;

    private String buildingLatitude;

    private String buildingHeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BigDecimal> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<BigDecimal> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Float> getVertexHeights() {
        return vertexHeights;
    }

    public void setVertexHeights(List<Float> vertexHeights) {
        this.vertexHeights = vertexHeights;
    }

    public String getBuildingLongitude() {
        return buildingLongitude;
    }

    public void setBuildingLongitude(String buildingLongitude) {
        this.buildingLongitude = buildingLongitude;
    }

    public String getBuildingLatitude() {
        return buildingLatitude;
    }

    public void setBuildingLatitude(String buildingLatitude) {
        this.buildingLatitude = buildingLatitude;
    }

    public String getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(String buildingHeight) {
        this.buildingHeight = buildingHeight;
    }
}
