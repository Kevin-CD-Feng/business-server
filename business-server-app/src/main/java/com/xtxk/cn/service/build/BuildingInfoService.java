package com.xtxk.cn.service.build;

import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.build.BuildingRelatedDeviceDto;
import com.xtxk.cn.dto.geogra.AddBuildingInfoReqDto;
import com.xtxk.cn.dto.geogra.BuildingLocationInfo;
import com.xtxk.cn.dto.geogra.UpdateBuildingInfoReqDto;
import com.xtxk.cn.dto.build.BuildingDeviceDto;
import com.xtxk.cn.entity.BuildingInfoPO;

import java.util.List;

/**
 * BuildingInfoService
 *
 * @author chenzhi
 * @date 2022/10/14 15:05
 * @description
 */
public interface BuildingInfoService {

    /**
     * 分页查询楼栋建筑列表
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<BuildingInfoPO> getBuildingInfoList(String keyWord, Integer pageNum, Integer pageSize);

    /**
     * 新增
     * @param addBuildingInfoReqDto
     */
    void addBuildingInfo(AddBuildingInfoReqDto addBuildingInfoReqDto);

    /**
     * 修改楼栋建筑
     * @param buildingId
     * @param updateBuildingInfoReqDto
     */
    void updateBuildingInfo(Integer buildingId, UpdateBuildingInfoReqDto updateBuildingInfoReqDto);

    /**
     * 删除
     * @param buildingId
     */
    void deleteBuildingInfo(Integer buildingId);

    /**
     * 获取需要导出的楼栋建筑列表
     * @param keyWord
     * @return
     */
    List<BuildingInfoPO> getExportBuildingInfoList(String keyWord);

    /**
     * 查询单个楼栋详情
     * @param buildingId
     * @return
     */
    BuildingInfoPO getBuildingInfo(Integer buildingId);

    /**
     * 获取所有楼栋位置信息
     * @return
     */
    List<BuildingLocationInfo> getAllBuildingLocationInfo();

    /**
     * 获取设备Id获取楼栋的信息
     * @return
     */
    BuildingInfoPO getBuildInfoByDeviceId(String deviceId);


    BuildingInfoPO getBuildingInfoByIdentity(String identityId);

    /**
     * 根据identityId查询楼栋关联的设备列表
     * @param identityId
     * @return
     */
    BuildingDeviceDto getBuildingDeviceListByIdentity(String identityId);

    /**
     * 查询楼栋列表(包含楼栋关联设备数量及状态)
     */
    List<BuildingRelatedDeviceDto> getBuildingRelatedDeviceList();
}
