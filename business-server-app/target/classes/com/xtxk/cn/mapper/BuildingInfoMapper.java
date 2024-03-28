package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.build.BuildingRelatedDeviceDto;
import com.xtxk.cn.dto.geogra.BuildingLocationInfo;
import com.xtxk.cn.dto.build.BuildingDevicePO;
import com.xtxk.cn.entity.BuildingInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BuildingInfoMapper {

    /**
     * 通过关键字查询
     * @param keyWord
     * @return
     */
    List<BuildingInfoPO> queryByKeyWord(@Param("keyWord") String keyWord);

    /**
     * 新增
     * @param buildingInfoPO
     */
    void add(BuildingInfoPO buildingInfoPO);

    /**
     * 修改
     * @param buildingInfoPO
     */
    void update(BuildingInfoPO buildingInfoPO);

    /**
     * 通过主键查询
     * @param buildingId
     * @return
     */
    BuildingInfoPO getById(@Param("buildingId")Integer buildingId);

    /**
     * 删除
     * @param buildingId
     */
    void deleteById(@Param("buildingId")Integer buildingId);

    /**
     * 获取所有楼栋的位置信息
     * @return
     */
    List<BuildingLocationInfo> getAllBuildingLocationInfo();

    BuildingInfoPO getBuildInfoByDeviceId(@Param("deviceId") String deviceId);

    BuildingInfoPO getBuildInfoByIdentityId(@Param("identityId") String identityId);

    /**
     * 根据identityId查询楼栋关联的设备列表
     * @param identityId
     * @return
     */
    List<BuildingDevicePO> getBuildingDeviceListByIdentityId(@Param("identityId") String identityId);

    /**
     * 查询楼栋列表(包含楼栋关联设备数量及状态)
     * @return
     */
    List<BuildingRelatedDeviceDto> getBuildingRelatedDeviceList();
}
