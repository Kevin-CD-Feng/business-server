package com.xtxk.cn.third.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.third.configuration.DataLakeConfiguration;
import com.xtxk.cn.third.dto.car.MockCarInfo;
import com.xtxk.cn.third.entity.car.CarInfo;
import com.xtxk.cn.third.entity.car.OilCarInformation;
import com.xtxk.cn.third.common.PageReqDto;
import com.xtxk.cn.third.mapper.CarInfoMapper;
import com.xtxk.cn.third.mapper.OilCarInformationMapper;
import com.xtxk.cn.third.mapper.TCarFlowMapper;
import com.xtxk.cn.third.service.BatchService;
import com.xtxk.cn.third.service.CarService;
import com.xtxk.cn.third.service.DateLakeService;
import com.xtxk.cn.third.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.xtxk.cn.third.util.RequestHeaderUtils.*;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 10:40
 */
@Service
public class CarServiceImpl implements CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private DataLakeConfiguration dataLakeConfiguration;
    @Autowired
    private DateLakeService dateLakeService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private CarInfoMapper carInfoMapper;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private OilCarInformationMapper oilCarInformationMapper;
    @Autowired
    private TCarFlowMapper tCarFlowMapper;

    @Override
    public Integer pullCarInfo(Integer page, Integer size) {
        String url = appendUrl(dataLakeConfiguration.getCarUrl(), page, size);
        logger.info("==========================>拉取小区车辆请求地址url: " + url);
        String token = dateLakeService.getToken();
        String response = httpUtils.post(url, token);
        if (StringUtils.isBlank(response)) {
            return 0;
        }
        JSONObject jsonObject = JSONUtil.toBean(response, JSONObject.class);
        JSONArray array = parseJsonData(jsonObject);
        List<OilCarInformation> carList = JSONUtil.toList(array, OilCarInformation.class);
        batchService.batchSave(carList, OilCarInformationMapper.class, OilCarInformationMapper::insertSelective);
        PageReqDto pageInfo = parsePage(jsonObject);
        if (pageInfo.isPage()) {
            Integer currentPage = pageInfo.getPageNo();
            Integer limit = pageInfo.getPageSize();
            Integer total = pageInfo.getTotal();
            logger.info(String.format(" pullCarInfo 当前页: %s , 条数 : %s ; 总条数: %s", currentPage, limit, total));
            pullCarInfo(currentPage, limit);
        }
        return pageInfo.getTotal();
    }

    /***
     * 同步小区车辆数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncCarInfo() {
        List<OilCarInformation> oilCarList = new ArrayList<>();
        List<CarInfo> carList = new ArrayList<>();
        Cursor<OilCarInformation> cursorCarList = oilCarInformationMapper.streamQueryCarList();
        cursorCarList.forEach(c -> oilCarList.add(c));
        Cursor<CarInfo> cursorInfo = carInfoMapper.streamQueryCarList();
        cursorInfo.forEach(c -> carList.add(c));
        // TODO 根据车牌号去重
        List<OilCarInformation> oilUseList = oilCarList.stream().filter(p -> p.getBSFLAG() == 1).collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(OilCarInformation::getVEHICLE_BRAND))), ArrayList::new));
        List<OilCarInformation> oilNoUseList = oilCarList.stream().filter(p -> p.getBSFLAG() != 1).collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(OilCarInformation::getVEHICLE_BRAND))), ArrayList::new));
        Map<String, CarInfo> carMap = carList.stream().collect(Collectors.toMap(p -> p.getLakeCarId(),
                Function.identity(), (key1, key2) -> key2));

        List<CarInfo> addList = new ArrayList<>();
        List<CarInfo> updateList = new ArrayList<>();
        List<Integer> deleteList = new ArrayList<>();
        for (OilCarInformation info : oilUseList) {
            CarInfo carInfo = carMap.get(info.getINFOR_PRIMARY_ID());
            if (ObjectUtil.isNotNull(carInfo)) {
                CarInfo updateCarInfo = updateCarInfo(info, carInfo);
                if (ObjectUtil.isNotNull(updateCarInfo)) {
                    updateList.add(updateCarInfo);
                }
            } else {
                addList.add(addCarInfo(info));
            }
        }

        for (OilCarInformation info : oilNoUseList) {
            CarInfo carInfo = carMap.get(info.getINFOR_PRIMARY_ID());
            if (ObjectUtil.isNotNull(carInfo)) {
                deleteList.add(carInfo.getCarId());
            }
        }

        if (addList != null && addList.size() > 0) {
            logger.info("批量插入车辆数量：" + addList.size());
            List<List<CarInfo>> splitList = ListUtil.partition(addList, 100);
            for (List<CarInfo> list : splitList) {
                logger.info("批量插入车辆数据 ===> {}", JSONUtil.toJsonStr(list));
                carInfoMapper.insertBatchSave(list);
            }
        }
        if (updateList != null && updateList.size() > 0) {
            logger.info("批量更新小人员数量：" + updateList.size());
            List<List<CarInfo>> splitList = ListUtil.partition(updateList, 100);
            for (List<CarInfo> list : splitList) {
                logger.info("批量更新车辆数据 ===> {}", JSONUtil.toJsonStr(list));
                carInfoMapper.updateBatchSave(list);
            }
        }
        if (deleteList != null && deleteList.size() > 0) {
            logger.info("批量删除车辆数量：" + deleteList.size());
            List<List<Integer>> splitList = ListUtil.partition(deleteList, 100);
            for (List<Integer> list : splitList) {
                logger.info("批量删除车辆数据 ===> {}", JSONUtil.toJsonStr(list));
                carInfoMapper.deleteBatchSave(list);
            }
        }
        return true;
    }

    /***
     * mock 车辆数据
     * @param mockInfo
     * @return
     */
    @Override
    public Boolean mockCarInfo(List<MockCarInfo> mockInfo) {
        if (ArrayUtil.isEmpty(mockInfo)) {
            return false;
        }
        List<CarInfo> list = new ArrayList<>();
        for (MockCarInfo info : mockInfo) {
            CarInfo carInfo = new CarInfo();
            carInfo.setCarNumber(info.getCardNumber());
            carInfo.setCarOwnerName(info.getVehicleOwner());
            carInfo.setCarType(info.getVehicleType());
            carInfo.setLakeCarId(info.getCarId());
            carInfo.setCreateTime(new Date());
            carInfo.setUpdateTime(new Date());
            list.add(carInfo);
        }
        return carInfoMapper.insertBatchSave(list) > 0;
    }


    private CarInfo addCarInfo(OilCarInformation carInformation) {
        CarInfo info = new CarInfo();
        info.setCarNumber(carInformation.getVEHICLE_BRAND());
        info.setCarOwnerName(carInformation.getVEHICLE_OWNER());
        info.setCarType(carInformation.getVEHICLE_TYPE());
        info.setLakeCarId(carInformation.getINFOR_PRIMARY_ID());
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        return info;
    }

    private CarInfo updateCarInfo(OilCarInformation info, CarInfo carInfo) {
        String oldValue = carInfo.getCarNumber() + carInfo.getCarOwnerName() + carInfo.getCarType();
        String value = info.getVEHICLE_BRAND() + info.getVEHICLE_OWNER() + info.getVEHICLE_TYPE();
        if (oldValue.equals(value)) {
            return null;
        }
        carInfo.setCarNumber(info.getVEHICLE_BRAND());
        carInfo.setCarOwnerName(info.getVEHICLE_OWNER());
        carInfo.setCarType(info.getVEHICLE_TYPE());
        carInfo.setLakeCarId(info.getINFOR_PRIMARY_ID());
        carInfo.setUpdateTime(new Date());
        return carInfo;
    }

}
