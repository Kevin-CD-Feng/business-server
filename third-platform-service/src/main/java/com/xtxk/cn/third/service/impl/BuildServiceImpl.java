package com.xtxk.cn.third.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.third.configuration.DataLakeConfiguration;
import com.xtxk.cn.third.dto.build.MockBuild;
import com.xtxk.cn.third.entity.build.BuildingInfo;
import com.xtxk.cn.third.entity.build.OilBuildInformation;
import com.xtxk.cn.third.common.PageReqDto;
import com.xtxk.cn.third.mapper.BuildingInfoMapper;
import com.xtxk.cn.third.mapper.OilBuildInformationMapper;
import com.xtxk.cn.third.service.BatchService;
import com.xtxk.cn.third.service.BuildService;
import com.xtxk.cn.third.service.DateLakeService;
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
 * @description 楼栋数据
 * @author liulei
 * @date 2023-08-22 10:39
 */
@Service
public class BuildServiceImpl implements BuildService {

    private static final Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);

    @Autowired
    private DataLakeConfiguration dataLakeConfiguration;
    @Autowired
    private DateLakeService dateLakeService;
    @Autowired
    private BatchService batchService;
    @Autowired
    private BuildingInfoMapper buildingInfoMapper;
    @Autowired
    private OilBuildInformationMapper oilBuildInformationMapper;
    @Autowired
    private HttpUtils httpUtils;

    /***
     * 拉取楼栋数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public Integer pullBuildInfo(Integer page, Integer size) {
        String url = appendUrl(dataLakeConfiguration.getBuildUrl(), page, size);
        logger.info("==========================>拉取小区楼栋请求地址url: " + url);
        String token = dateLakeService.getToken();
        String response = httpUtils.post(url, token);
        if (StringUtils.isBlank(response)) {
            return 0;
        }
        JSONObject jsonObject = JSONUtil.toBean(response, JSONObject.class);
        JSONArray array = parseJsonData(jsonObject);
        List<OilBuildInformation> buildList = JSONUtil.toList(array, OilBuildInformation.class);
        batchService.batchSave(buildList, OilBuildInformationMapper.class, OilBuildInformationMapper::insertSelective);
        PageReqDto pageInfo = parsePage(jsonObject);
        if (pageInfo.isPage()) {
            Integer currentPage = pageInfo.getPageNo();
            Integer limit = pageInfo.getPageSize();
            Integer total = pageInfo.getTotal();
            logger.info(String.format(" pullBuildInfo 当前页: %s , 条数 : %s ; 总条数: %s", currentPage, limit, total));
            pullBuildInfo(currentPage, limit);
        }
        return pageInfo.getTotal();
    }

    /***
     *  同步楼栋数据
     * @return
     */
    @Override
    public Boolean syncBuild() {
        List<OilBuildInformation> oilBuildList = oilBuildInformationMapper.selectList();
        List<BuildingInfo> buildList = buildingInfoMapper.selectList();

        List<OilBuildInformation> useBuildList = oilBuildList.stream().filter(b -> b.getBSFLAG() == 1).collect(Collectors.toList());
        List<OilBuildInformation> useNoBuildList = oilBuildList.stream().filter(b -> b.getBSFLAG() != 1).collect(Collectors.toList());
        Map<String, BuildingInfo> buildMap = buildList.stream().collect(Collectors.toMap(p -> p.getLakeBuildingId(),
                Function.identity(), (key1, key2) -> key2));

        List<BuildingInfo> addList = new ArrayList<>();
        List<BuildingInfo> updateList = new ArrayList<>();
        List<Integer> removeList = new ArrayList<>();
        for (OilBuildInformation build : useBuildList) {
            BuildingInfo buildingInfo = buildMap.get(build.getBUILD_ID());
            if (ObjectUtil.isNotNull(buildingInfo)) {
                BuildingInfo updateBuildingInfo = updateBuildInfo(build, buildingInfo);
                if (ObjectUtil.isNotNull(updateBuildingInfo)) {
                    updateList.add(updateBuildingInfo);
                }
            } else {
                addList.add(addBuildInfo(build));
            }
        }

        for (OilBuildInformation info : useNoBuildList) {
            BuildingInfo buildingInfo = buildMap.get(info.getBUILD_ID());
            if (ObjectUtil.isNotNull(buildingInfo)) {
                removeList.add(buildingInfo.getBuildingId());
            }
        }

        if (ArrayUtil.isNotEmpty(addList)) {
            logger.info("批量插入楼栋数量：" + addList.size());
            List<List<BuildingInfo>> splitList = ListUtil.partition(addList, 100);
            for (List<BuildingInfo> list : splitList) {
                logger.info("批量插入楼栋数据 ===> {}", JSONUtil.toJsonStr(list));
                buildingInfoMapper.insertBatchSave(list);
            }
        }
        if (ArrayUtil.isNotEmpty(updateList)) {
            logger.info("批量更新楼栋数量：" + updateList.size());
            List<List<BuildingInfo>> splitList = ListUtil.partition(updateList, 100);
            for (List<BuildingInfo> list : splitList) {
                logger.info("批量更新楼栋数据 ===> {}", JSONUtil.toJsonStr(list));
                buildingInfoMapper.updateBatchSave(list);
            }
        }
        if (ArrayUtil.isNotEmpty(removeList)) {
            logger.info("批量删除楼栋数量：" + removeList.size());
            List<List<Integer>> splitList = ListUtil.partition(removeList, 100);
            for (List<Integer> list : splitList) {
                logger.info("批量删除楼栋数据 ===> {}", JSONUtil.toJsonStr(list));
                buildingInfoMapper.deleteBatchSave(list);
            }
        }
        return true;
    }

    /***
     * mock
     * @return
     * @param buildList
     */
    @Override
    public Boolean mockInfo(List<MockBuild> buildList) {
        if (ArrayUtil.isEmpty(buildList)) {
            return false;
        }
        List<BuildingInfo> list = new ArrayList<>();
        for (MockBuild build : buildList) {
            BuildingInfo info = new BuildingInfo();
            info.setBuildingName(build.getBuildName());
            info.setBuildingNumber(build.getBuildCode());
            info.setBuildingType(build.getBuildType());
            info.setFloorSpace(build.getBuildUpArea());
            info.setBuildingFloorCount(build.getFloorNum());
            info.setLakeBuildingId(build.getBuildId());
            info.setCreateTime(new Date());
            info.setUpdateTime(new Date());
            list.add(info);
        }
        return buildingInfoMapper.insertBatchSave(list) > 0;
    }

    /***
     * 查询楼栋数据
     * @return
     */
    @Override
    public List<BuildingInfo> selectByLakeBuild() {
        return buildingInfoMapper.selectByLakeBuild();
    }


    /***
     * 添加楼栋数据
     * @param build
     * @return
     */
    private BuildingInfo addBuildInfo(OilBuildInformation build) {
        BuildingInfo info = new BuildingInfo();
        info.setBuildingName(build.getBUILD_NAME());
        info.setBuildingNumber(build.getBUILD_CODE());
        info.setBuildingType(build.getBUILD_TYPE());
        info.setDistrict(build.getVILLAGE_ID());
        info.setFloorSpace(build.getBUILT_UP_AREA().doubleValue());
        info.setBuildingFloorCount(build.getFLOOR_NUM().intValue());
        info.setLakeBuildingId(build.getBUILD_ID());
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        return info;
    }

    /***
     * 更新
     * @param build
     * @param buildingInfo
     * @return
     */
    private BuildingInfo updateBuildInfo(OilBuildInformation build, BuildingInfo buildingInfo) {
        StringBuilder oldBuild = new StringBuilder()
                .append(buildingInfo.getBuildingName())
                .append(buildingInfo.getBuildingType())
                .append(buildingInfo.getBuildingNumber())
                .append(buildingInfo.getFloorSpace().doubleValue())
                .append(buildingInfo.getBuildingFloorCount());

        StringBuilder stringBuilder = new StringBuilder()
                .append(build.getBUILD_NAME())
                .append(build.getBUILD_TYPE())
                .append(build.getBUILD_CODE())
                .append(build.getBUILT_UP_AREA().doubleValue())
                .append(build.getFLOOR_NUM().intValue());

        if (oldBuild.toString().equals(stringBuilder.toString())) {
            return null;
        }
        buildingInfo.setBuildingName(build.getBUILD_NAME());
        buildingInfo.setBuildingType(build.getBUILD_TYPE());
        buildingInfo.setBuildingNumber(build.getBUILD_CODE());
        buildingInfo.setDistrict(build.getVILLAGE_ID());
        buildingInfo.setFloorSpace(build.getBUILT_UP_AREA().doubleValue());
        buildingInfo.setBuildingFloorCount(build.getFLOOR_NUM().intValue());
        buildingInfo.setUpdateTime(new Date());
        return buildingInfo;
    }


}
