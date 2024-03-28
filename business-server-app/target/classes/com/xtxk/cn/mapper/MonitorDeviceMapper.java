package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.geogra.GeograObjDto;
import com.xtxk.cn.dto.geogra.MonitorDeviceDto;
import com.xtxk.cn.dto.monitor.DeviceAndGateStateRspDto;
import com.xtxk.cn.dto.monitor.DeviceCntByAreaRspDto;
import com.xtxk.cn.dto.monitor.DeviceStatusRspDto;
import com.xtxk.cn.dto.monitor.ResInfoForMapRspDto;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.entity.PerCarDevCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MonitorDeviceMapper {

    /**
     * 通过关键字查询
     *
     * @param keyWord
     * @return
     */
    List<MonitorDevicePO> queryByKeyWord(@Param("keyWord") String keyWord,
                                         @Param("deviceStateName") Integer deviceStateName,
                                         @Param("districtName") String districtName);

    /**
     * 新增
     *
     * @param monitorDevicePO
     */
    void add(MonitorDevicePO monitorDevicePO);

    /**
     * 修改
     *
     * @param monitorDevicePO
     */
    void update(MonitorDevicePO monitorDevicePO);

    /**
     * 通过设备id查询
     *
     * @param deviceId
     * @return
     */
    MonitorDevicePO getById(@Param("deviceId") String deviceId);

    /**
     * 删除设备
     *
     * @param deviceId
     */
    void deleteById(@Param("deviceId") String deviceId);

    /**
     * 获取人、车、设备统计数量
     *
     * @return
     */
    PerCarDevCount getPerCarDevCount();

    /**
     * 获取区域下的设备列表
     *
     * @return
     */
    List<MonitorDeviceDto> getDistrictDevice();

    /**
     * 查询设备总数
     *
     * @return
     */
    Integer getTotalCount();

    /**
     * 获取地理信息结果
     *
     * @return
     */
    List<GeograObjDto> getGeograObjDto();

    /**
     * 获取所有设备的id
     *
     * @return
     */
    List<String> getAllDeviceId();

    /**
     * 获取所有设备信息(非所有字段)
     *
     * @return
     */
    List<MonitorDevicePO> getAll();

    /**
     * 批量更新设备状态
     */
    void batchUpdateState(List<MonitorDevicePO> list);

    /**
     * 获取所有设备信息(所有字段)
     *
     * @return
     */
    List<MonitorDevicePO> getAllList();

    /**
     * 获取设备的在线和非在线设备数量
     *
     * @return
     */
    List<DeviceStatusRspDto> queryDeviceStatus();

    List<DeviceCntByAreaRspDto> queryDeviceByArea();

    List<DeviceAndGateStateRspDto> queryDeviceAndGateStatus();

    List<ResInfoForMapRspDto> queryAllResInfoForMap(@Param("category") String category);

    /**
     * 根据组织id查询设备列表
     *
     * @param directoryId
     * @return
     */
    List<MonitorDevicePO> getDeviceListByDirectoryId(String directoryId);

    /**
     * 查询组织下属设备数量
     *
     * @param directoryId
     * @return
     */
    Integer getDirectoryDeviceCount(String directoryId);

    /**
     * 根据设备名称检索设备列表
     *
     * @param deviceName
     * @return
     */
    List<MonitorDevicePO> searchDeviceListByName(String deviceName);

    /**
     * 根据deviceId获取sipId
     * @param deviceId
     * @return
     */
    String getSipIdByDeviceId(String deviceId);

    /**
     * 根据设备id查询海康设备code
     * @param deviceId
     * @return
     */
    String getCameraCodeByDeviceId(String deviceId);
}
