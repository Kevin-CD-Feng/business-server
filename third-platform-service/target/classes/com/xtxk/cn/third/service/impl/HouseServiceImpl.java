package com.xtxk.cn.third.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.third.configuration.DataLakeConfiguration;
import com.xtxk.cn.third.dto.house.MockHouse;
import com.xtxk.cn.third.entity.build.BuildingInfo;
import com.xtxk.cn.third.entity.house.HouseInfo;
import com.xtxk.cn.third.entity.house.OilHouseHold;
import com.xtxk.cn.third.entity.house.OilHouseInformation;
import com.xtxk.cn.third.common.PageReqDto;
import com.xtxk.cn.third.mapper.HouseInfoMapper;
import com.xtxk.cn.third.mapper.OilHouseHoldMapper;
import com.xtxk.cn.third.mapper.OilHouseInformationMapper;
import com.xtxk.cn.third.service.BatchService;
import com.xtxk.cn.third.service.BuildService;
import com.xtxk.cn.third.service.DateLakeService;
import com.xtxk.cn.third.service.HouseService;
import com.xtxk.cn.third.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static com.xtxk.cn.third.util.RequestHeaderUtils.*;

/***
 * @description 房屋数据
 * @author liulei
 * @date 2023-08-22 10:42
 */
@Service
public class HouseServiceImpl implements HouseService {
    private static final Logger logger = LoggerFactory.getLogger(HouseServiceImpl.class);

    @Autowired
    private DataLakeConfiguration dataLakeConfiguration;
    @Autowired
    private DateLakeService dateLakeService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private BuildService buildService;
    @Autowired
    private HouseInfoMapper houseInfoMapper;
    @Autowired
    private OilHouseInformationMapper oilHouseInformationMapper;

    /***
     * 拉取出租屋数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public Integer pullHouseInfo(Integer page, Integer size) {
        String url = appendUrl(dataLakeConfiguration.getHouseUrl(), page, size);
        logger.info("==========================>拉取小区出租屋请求地址url: " + url);
        String token = dateLakeService.getToken();
        String response = httpUtils.post(url, token);
        if (StringUtils.isBlank(response)) {
            return 0;
        }
        JSONObject jsonObject = JSONUtil.toBean(response, JSONObject.class);
        JSONArray array = parseJsonData(jsonObject);
        List<OilHouseInformation> houseList = JSONUtil.toList(array, OilHouseInformation.class);
        batchService.batchSave(houseList, OilHouseInformationMapper.class, OilHouseInformationMapper::insertSelective);
        PageReqDto pageInfo = parsePage(jsonObject);
        if (pageInfo.isPage()) {
            Integer currentPage = pageInfo.getPageNo();
            Integer limit = pageInfo.getPageSize();
            Integer total = pageInfo.getTotal();
            logger.info(String.format(" pullHouseInfo 当前页: %s , 条数 : %s ; 总条数: %s", currentPage, limit, total));
            pullHouseInfo(currentPage, limit);
        }
        return pageInfo.getTotal();
    }

    /***
     *  同步出租屋数据
     * @return
     */
    @Override
    public Boolean syncHouse() {
        List<OilHouseInformation> oilHouseList = oilHouseInformationMapper.selectList();
        List<HouseInfo> houseList = houseInfoMapper.selectList();
        List<OilHouseInformation> useList = oilHouseList.stream().filter(h -> h.getBSFLAG() == 1).collect(Collectors.toList());
        List<OilHouseInformation> useNoList = oilHouseList.stream().filter(h -> h.getBSFLAG() != 1).collect(Collectors.toList());
        Map<String, HouseInfo> houseMap = houseList.stream().collect(Collectors.toMap(p -> p.getLakeHouseId(),
                Function.identity(), (key1, key2) -> key2));

        List<HouseInfo> addList = new ArrayList<>();
        List<HouseInfo> updateList = new ArrayList<>();
        List<Integer> removeList = new ArrayList<>();
        for (OilHouseInformation houseInfo : useList) {
            String houseId = houseInfo.getHOUSE_ID();
            HouseInfo info = houseMap.get(houseId);
            if (ObjectUtil.isNotNull(info)) {
                HouseInfo updateHouseInfo = updateHouseInfo(houseInfo, info);
                if (ObjectUtil.isNotNull(updateHouseInfo)) {
                    updateList.add(updateHouseInfo);
                }
            } else {
                addList.add(addHouseInfo(houseInfo));
            }
        }

        for (OilHouseInformation house : useNoList) {
            HouseInfo info = houseMap.get(house.getHOUSE_ID());
            if (ObjectUtil.isNotNull(info)) {
                removeList.add(info.getHouseId());
            }
        }

        if (ArrayUtil.isNotEmpty(addList)) {
            logger.info("批量插入房屋数量：" + addList.size());
            List<List<HouseInfo>> splitList = ListUtil.partition(addList, 100);
            for (List<HouseInfo> list : splitList) {
                logger.info("批量插入房屋数据 ===> {}", JSONUtil.toJsonStr(list));
                houseInfoMapper.insertBatchSave(list);
            }
        }
        if (ArrayUtil.isNotEmpty(updateList)) {
            logger.info("批量更新小房屋数量：" + updateList.size());
            List<List<HouseInfo>> splitList = ListUtil.partition(updateList, 100);
            for (List<HouseInfo> list : splitList) {
                logger.info("批量更新房屋数据 ===> {}", JSONUtil.toJsonStr(list));
                houseInfoMapper.updateBatchSave(list);
            }
        }
        if (ArrayUtil.isNotEmpty(removeList)) {
            logger.info("批量删除房屋数量：" + removeList.size());
            List<List<Integer>> splitList = ListUtil.partition(removeList, 100);
            for (List<Integer> list : splitList) {
                logger.info("批量删除房屋数据 ===> {}", JSONUtil.toJsonStr(list));
                houseInfoMapper.deleteBatchSave(list);
            }
        }
        return true;
    }

    @Override
    public Integer pullHouseHoldInfo(Integer page, Integer size) {
        String url = appendUrl(dataLakeConfiguration.getHouseHoldUrl(), page, size);
        logger.info("==========================>拉取小区房屋住户请求地址url: " + url);
        String token = dateLakeService.getToken();
        String response = httpUtils.post(url, token);
        if (StringUtils.isBlank(response)) {
            return 0;
        }
        JSONObject jsonObject = JSONUtil.toBean(response, JSONObject.class);
        JSONArray array = parseJsonData(jsonObject);
        List<OilHouseHold> houseHoldList = JSONUtil.toList(array, OilHouseHold.class);
        batchService.batchSave(houseHoldList, OilHouseHoldMapper.class, OilHouseHoldMapper::insertSelective);
        PageReqDto pageInfo = parsePage(jsonObject);
        if (pageInfo.isPage()) {
            Integer currentPage = pageInfo.getPageNo();
            Integer limit = pageInfo.getPageSize();
            Integer total = pageInfo.getTotal();
            logger.info(String.format(" pullHouseHoldInfo 当前页: %s , 条数 : %s ; 总条数: %s", currentPage, limit, total));
            pullHouseHoldInfo(currentPage,limit);
        }
        return pageInfo.getTotal();
    }

    @Override
    public Boolean syncHouseHoldInfo() {
        return null;
    }

    /***
     * 添加房屋信息
     * @param info
     * @return
     */
    private HouseInfo addHouseInfo(OilHouseInformation info) {
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setHouseCode(info.getHOUSE_CODE());
        houseInfo.setBuildId(info.getBuildId());
        houseInfo.setHouseNature(info.getHOUSE_NATURE());
        houseInfo.setHouseArea(info.getUSE_AREA().doubleValue());
        houseInfo.setLakeHouseId(info.getHOUSE_ID());
        houseInfo.setCreateTime(new Date());
        houseInfo.setUpdateTime(new Date());
        return houseInfo;
    }

    /***
     * 更新房屋信息
     * @param info
     * @param houseInfo
     * @return
     */
    private HouseInfo updateHouseInfo(OilHouseInformation info, HouseInfo houseInfo) {
        StringBuilder oldBuild = new StringBuilder();
        StringBuilder build = new StringBuilder();

        oldBuild.append(houseInfo.getHouseCode())
                .append(houseInfo.getBuildId())
                .append(houseInfo.getHouseNature());

        build.append(info.getHOUSE_CODE())
                .append(info.getBuildId())
                .append(info.getHOUSE_NATURE());

        if (oldBuild.toString().equals(build.toString())) {
            return null;
        }

        houseInfo.setHouseCode(info.getHOUSE_CODE());
        houseInfo.setBuildId(info.getBuildId());
        houseInfo.setHouseNature(info.getHOUSE_NATURE());
        houseInfo.setHouseArea(info.getUSE_AREA().doubleValue());
        houseInfo.setLakeHouseId(info.getHOUSE_ID());
        houseInfo.setUpdateTime(new Date());
        return houseInfo;
    }

    /***
     * mock
     * @param houseList
     * @return
     */
    @Override
    public Boolean mockHouseInfo(List<MockHouse> houseList) {
        if (ArrayUtil.isEmpty(houseList)) {
            return false;
        }
        List<HouseInfo> houseInfoList = new ArrayList<>();
        List<BuildingInfo> buildList = buildService.selectByLakeBuild();
        Map<String, BuildingInfo> buildMap = buildList.stream().collect(Collectors.toMap(p -> p.getLakeBuildingId(),
                Function.identity(), (key1, key2) -> key2));
        for (MockHouse house : houseList) {
            String buildId = house.getBuildId();
            BuildingInfo buildingInfo = buildMap.get(buildId);
            if (ObjectUtil.isNotNull(buildingInfo)) {
                HouseInfo houseInfo = new HouseInfo();
                houseInfo.setHouseCode(house.getHouseCode());
                houseInfo.setBuildId(buildingInfo.getBuildingId());
                houseInfo.setHouseNature(house.getHouseNature());
                houseInfo.setHouseArea(house.getBuildUpArea().doubleValue());
                houseInfo.setLakeHouseId(house.getHouseId());
                houseInfo.setCreateTime(new Date());
                houseInfo.setUpdateTime(new Date());
                houseInfoList.add(houseInfo);
            }

        }
        return houseInfoMapper.insertBatchSave(houseInfoList) > 0;
    }

}
