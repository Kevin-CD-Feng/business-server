package com.xtxk.cn.third.entity.build;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class BuildingInfo implements Serializable {

    private static final long serialVersionUID = -6974917552019248744L;
    private Integer buildingId;

    private String buildingNumber;

    private String buildingName;

    private String buildingManagerName;

    private String buildingManagerPhone;

    private String district;

    private String buildingType;

    private Integer buildingDoorCount;

    private Integer buildingPersonCount;

    private Integer buildingFloorCount;

    private Double floorSpace;

    private String buildingLongitude;

    private String buildingLatitude;

    private String address;

    private Date createTime;

    private Date updateTime;

    private String buildingHeight;

    private String lakeBuildingId;

}