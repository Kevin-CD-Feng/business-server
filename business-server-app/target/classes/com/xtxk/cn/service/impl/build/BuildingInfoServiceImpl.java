package com.xtxk.cn.service.impl.build;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.xtxk.cn.dto.build.BuildingMonitorDeviceDto;
import com.xtxk.cn.dto.build.BuildingRelatedDeviceDto;
import com.xtxk.cn.dto.geogra.AddBuildingInfoReqDto;
import com.xtxk.cn.dto.geogra.BuildingLocationInfo;
import com.xtxk.cn.dto.geogra.UpdateBuildingInfoReqDto;
import com.xtxk.cn.dto.build.BuildingDeviceDto;
import com.xtxk.cn.dto.build.BuildingDevicePO;
import com.xtxk.cn.entity.BuildingInfoPO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.BuildingInfoMapper;
import com.xtxk.cn.service.build.BuildingInfoService;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BuildingInfoServiceImpl
 *
 * @author chenzhi
 * @date 2022/10/14 15:06
 * @description
 */
@Service
public class BuildingInfoServiceImpl implements BuildingInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingInfoServiceImpl.class);

    @Autowired
    private BuildingInfoMapper buildingInfoMapper;

    @Autowired
    private DicItemHandler dicItemHandler;

    @Override
    public PageInfo<BuildingInfoPO> getBuildingInfoList(String keyWord, Integer pageNum, Integer pageSize) {
        PageMethod.startPage(pageNum, pageSize);
        List<BuildingInfoPO> list = buildingInfoMapper.queryByKeyWord(keyWord);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
                e.setBuildingTypeName(mapping.get(e.getBuildingType()));
                e.setAddress(String.format("塔指%s%s栋", mapping.get(e.getDistrict()), e.getBuildingNumber()));
            });
        }
        return new PageInfo<>(list);
    }

    @Override
    public void addBuildingInfo(AddBuildingInfoReqDto addBuildingInfoReqDto) {
        BuildingInfoPO po = new BuildingInfoPO();
        BeanUtils.copyProperties(addBuildingInfoReqDto, po);
        buildingInfoMapper.add(po);
    }

    @Override
    public void updateBuildingInfo(Integer buildingId, UpdateBuildingInfoReqDto updateBuildingInfoReqDto) {
        BuildingInfoPO po = buildingInfoMapper.getById(buildingId);
        if (null == po) {
            LOGGER.info("buildingId:{} is not exist", buildingId);
            throw new ServiceException(ErrorCode.BUILDING_ID_NOT_EXIST);
        }
        BeanUtils.copyProperties(updateBuildingInfoReqDto, po);
        buildingInfoMapper.update(po);
    }

    @Override
    public void deleteBuildingInfo(Integer buildingId) {
        BuildingInfoPO po = buildingInfoMapper.getById(buildingId);
        if (null == po) {
            LOGGER.info("buildingId:{} is not exist", buildingId);
            throw new ServiceException(ErrorCode.BUILDING_ID_NOT_EXIST);
        }
        buildingInfoMapper.deleteById(buildingId);
    }

    @Override
    public List<BuildingInfoPO> getExportBuildingInfoList(String keyWord) {
        List<BuildingInfoPO> list = buildingInfoMapper.queryByKeyWord(keyWord);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setDistrictName(mapping.get(e.getDistrict()));
                e.setBuildingTypeName(mapping.get(e.getBuildingType()));
            });
        }
        return list;
    }

    @Override
    public BuildingInfoPO getBuildingInfo(Integer buildingId) {
        BuildingInfoPO po = buildingInfoMapper.getById(buildingId);
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        po.setDistrictName(mapping.get(po.getDistrict()));
        po.setBuildingTypeName(mapping.get(po.getBuildingType()));
        po.setAddress(String.format("塔指%s%s栋", mapping.get(po.getDistrict()), po.getBuildingNumber()));
        return po;
    }

    @Override
    public List<BuildingLocationInfo> getAllBuildingLocationInfo() {
        List<BuildingLocationInfo> list = buildingInfoMapper.getAllBuildingLocationInfo();
        if (!CollectionUtils.isEmpty(list)) {
            for (BuildingLocationInfo info : list) {
                String id = info.getId();
                String name = info.getName();
                List<BigDecimal> coordinates = info.getCoordinates();
                List<Float> vertexHeights = info.getVertexHeights();
                String buildingLongitude = info.getBuildingLongitude();
                String buildingLatitude = info.getBuildingLatitude();
                String buildingHeight = info.getBuildingHeight();
                if (StringUtils.isBlank(buildingLongitude)) {
                    LOGGER.warn("{} 缺失经度信息", name);
                    continue;
                }
                if (StringUtils.isBlank(buildingLatitude)) {
                    LOGGER.warn("{} 缺失纬度信息", name);
                    continue;
                }
                String[] lonArr = buildingLongitude.split(",");
                String[] latArr = buildingLatitude.split(",");
                if (lonArr.length != latArr.length) {
                    LOGGER.warn("{} 经纬度信息不匹配", name);
                    continue;
                }

                if (StringUtils.isBlank(buildingHeight)) {
                    LOGGER.warn("{} 缺失高度信息", name);
                    continue;
                }
                try {
                    for (int i = 0; i < lonArr.length; i++) {
                        String buildingLon = lonArr[i];
                        String buildingLat = latArr[i];
                        BigDecimal longitude = new BigDecimal(buildingLon);
                        BigDecimal latitude = new BigDecimal(buildingLat);
                        coordinates.add(longitude);
                        coordinates.add(latitude);
                    }
                    String[] heightArr = buildingHeight.split(",");
                    for (int i = 0; i < heightArr.length; i++) {
                        String height = heightArr[i];
                        vertexHeights.add(new Float(height));
                    }
                } catch (Exception e) {
                    LOGGER.error("id:{},name:{}", id, name, e.getMessage());
                    continue;
                }
            }
        }
        return list;
    }

    @Override
    public BuildingInfoPO getBuildInfoByDeviceId(String deviceId) {
        return this.buildingInfoMapper.getBuildInfoByDeviceId(deviceId);
    }

    @Override
    public BuildingInfoPO getBuildingInfoByIdentity(String identityId) {
        return this.buildingInfoMapper.getBuildInfoByIdentityId(identityId);
    }

    @Override
    public BuildingDeviceDto getBuildingDeviceListByIdentity(String identityId) {
        List<BuildingDevicePO> list = buildingInfoMapper.getBuildingDeviceListByIdentityId(identityId);
        BuildingDeviceDto dto = new BuildingDeviceDto();
        if (list.size() > 0) {
            List<BuildingMonitorDeviceDto> deviceList = new ArrayList<>();
            list.forEach(info -> {
                if (info.getDeviceId() != null) {
                    BuildingMonitorDeviceDto device = new BuildingMonitorDeviceDto();
                    device.setDeviceId(info.getDeviceId());
                    device.setDeviceName(info.getDeviceName());
                    device.setDeviceState(info.getDeviceState());
                    deviceList.add(device);
                }
            });

            dto.setBuildingId(list.get(0).getBuildingId());
            dto.setBuildingIdentity(list.get(0).getBuildingIdentity());
            dto.setBuildingName(list.get(0).getBuildingName());
            dto.setDeviceList(deviceList);
        }
        return dto;
    }

    @Override
    public List<BuildingRelatedDeviceDto> getBuildingRelatedDeviceList() {
        List<BuildingRelatedDeviceDto> list = buildingInfoMapper.getBuildingRelatedDeviceList();
        list.forEach(info -> {
            BuildingDeviceDto byIdentity = getBuildingDeviceListByIdentity(info.getIdentityId());
            info.setDeviceNum(byIdentity.getDeviceList().size());
            for (BuildingMonitorDeviceDto device : byIdentity.getDeviceList()) {
                if (device.getDeviceState() == 1) {
                    info.setIsDeviceOffline(true);
                    break;
                }
            }
        });
        return list;
    }
}
