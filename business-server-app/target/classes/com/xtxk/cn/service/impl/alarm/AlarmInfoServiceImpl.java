package com.xtxk.cn.service.impl.alarm;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.alarmInfo.*;
import com.xtxk.cn.dto.alarmInfo.AlarmQryInfo;
import com.xtxk.cn.entity.*;
import com.xtxk.cn.enums.*;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.*;
import com.xtxk.cn.service.alarm.AlarmInfoService;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import com.xtxk.cn.utils.http.HutoolHttpClient;
import com.xtxk.cn.utils.image.Graphics2dUtil;
import com.xtxk.cn.utils.image.ImageUtils;
import com.xtxk.cn.utils.object.ObjectCovertUtils;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class AlarmInfoServiceImpl implements AlarmInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoServiceImpl.class);

    @Autowired
    private AlarmInfoMapper alarmInfoMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private AlarmDealInfoMapper alarmDealInfoMapper;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Autowired
    private DicItemHandler dicItemHandler;

    @Autowired
    private AlarmViolationsDetailInfoMapper alarmViolationsDetailInfoMapper;

    @Autowired
    private VideoManager videoManager;

    @Value("${app.messageUrl}")
    private String messageUrl;

    @Value("${uploadFileBasePath}")
    private String uploadFileBasePath;

    @Value("${fileServicePath}")
    private String fileServicePath;

    @Value("${record.downloadUrl}")
    private String downloadUrl;

    @Value("${record.fromTimeDelay}")
    private Integer fromTimeDelay;

    @Value("${record.toTimeDelay}")
    private Integer toTimeDelay;

    @Value("${record.uploadDelay}")
    private Integer uploadDelay;


    @Override
    public List<AlarmInfo> getLatestAlarmInfo() {
        List<AlarmInfo> list = alarmInfoMapper.getLatestUndealedOrDealing();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setEventName(mapping.get(e.getEvent()));
                e.setAreaName(e.getArea());
                e.setResourceTypeName(mapping.get(e.getResourceType()));
                e.setAlarmType(dicItemMapper.getAlarmType(e.getEvent()));
                if (Strings.isEmpty(e.getAuditStatus())) {
                    e.setAuditStatus("none");
                    e.setStatus(1);
                }
            });
        }
        return list;
    }

    public static String getPercent(int x, int total) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) x / (float) total * 100);
        return result + "%";
    }

    @Override
    public List<AlarmStatusCount> getAlarmDealSit(Integer time) {
        String s = TimeQryTypeEnum.queryDescByCode(time);
        if (null == s) {
            LOGGER.info("time is error,time:{}", time);
            throw new ServiceException(ErrorCode.PARAMS_INLEGAL);
        }
        // 获取总数量
        Integer total = alarmInfoMapper.getTotalByTime(time);
        List<AlarmStatusCount> list = alarmInfoMapper.getAlarmStatusCountByTime(time);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(alarm -> {
                alarm.setStatusStr(AlarmEventStateEnum.queryDescByCode(alarm.getStatus()));
                Integer count = alarm.getCount();
                // 计算百分比
                String percent = getPercent(count, total);
                alarm.setPercent(percent);
            });
        }
        return list;
    }

    @Override
    public Map<String, List<AlarmLimit3ItemDto>> showAlarmInfos() {

        List<AlarmInfoByJingWeiDto> alarmInfos = alarmInfoMapper.getAlarmInfosGroupAndLimit3();


        if (alarmInfos != null && alarmInfos.size() > 0) {
            List<AlarmLimit3ItemDto> alarmLimit3ItemDtos = new ArrayList<>();
            alarmInfos.forEach(item -> {
                AlarmLimit3ItemDto alarmLimit3ItemDto = ObjectCovertUtils.doToDto(item, AlarmLimit3ItemDto.class);
                alarmLimit3ItemDto.setEventName(dicItemMapper.queryItemNameByItemCode(item.getEvent()));
                alarmLimit3ItemDto.setStatusName(AlarmStatusEnum.queryDescByCode(item.getStatus()));
                alarmLimit3ItemDto.setEventCode(item.getEvent());
                alarmLimit3ItemDto.setStatusCode(item.getStatus());
                alarmLimit3ItemDto.setResourceTypeDesc(dicItemMapper.queryItemNameByItemCode(item.getResourceType()));
                alarmLimit3ItemDtos.add(alarmLimit3ItemDto);
            });
            Map<String, List<AlarmLimit3ItemDto>> collect = alarmLimit3ItemDtos.stream().
                    collect(Collectors.groupingBy(new Function<AlarmLimit3ItemDto, String>() {
                        @Override
                        public String apply(AlarmLimit3ItemDto alarmInfo) {
                            return alarmInfo.getLongitude() + "," + alarmInfo.getLatitude();
                        }
                    }));
            return collect;
        } else {
            throw new ServiceException(ErrorCode.QUERY_AREA_ALARM_COUNT_ERROR_BY_TYPE);
        }
    }

    @Override
    public AreaAlarmCountRespDto getAreaAlarmCountByType(int timeType) {
        List<AreaItemDto> areaItemDtos = new ArrayList<>();
        if (timeType > 4) {
            throw new ServiceException(ErrorCode.QUERY_AREA_ALARM_COUNT_ERROR_BY_TYPE);
        } else {
            areaItemDtos = alarmInfoMapper.getAreaAlarmCountByType(timeType);
            if (areaItemDtos != null) {
                List<DicItem> dicItems = dicItemMapper.queryDicItemList("district_type");
                if (dicItems != null && dicItems.size() > 0) {
                    AreaAlarmCountRespDto areaAlarmCountRespDto = new AreaAlarmCountRespDto();
                    if (areaItemDtos.size() != dicItems.size()) {
                        List<String> source = new ArrayList<>();
                        dicItems.forEach(item -> {
                            source.add(item.getItemCode());
                        });
                        List<String> target = new ArrayList<>();
                        for (AreaItemDto areaItemDto : areaItemDtos) {
                            areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                            target.add(areaItemDto.getAreaCode());
                        }
                        source.removeAll(target);
                        for (String areaCode : source) {
                            AreaItemDto areaItemDto = new AreaItemDto();
                            areaItemDto.setAreaCode(areaCode);
                            areaItemDto.setAreaCount(0);
                            areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                            areaItemDtos.add(areaItemDto);
                        }

                    } else {
                        for (AreaItemDto areaItemDto : areaItemDtos) {
                            areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                        }
                    }
                    List<AreaItemDto> collect = areaItemDtos.stream()
                            .sorted(Comparator.comparing(AreaItemDto::getAreaCount)
                                    .reversed())
                            .collect(Collectors.toList());
                    areaAlarmCountRespDto.setAreaItemDtos(collect.subList(0, 5));
                    return areaAlarmCountRespDto;
                } else {
                    return new AreaAlarmCountRespDto();
                }

            } else {
                return new AreaAlarmCountRespDto();
            }
        }
    }

    @Override
    public AlarmTypeRatioRespDto getAlarmTypeRatioByType(int timeType) {
        List<AlarmTypeItemDto> alarmTypeItemDtos = new ArrayList<>();
        List<AlarmTypeItemDto> retDtos = new ArrayList<>();

        List<DicItem> alarm_event = this.dicItemHandler.getAllDicItemByDicCode("alarm_event");

        if (timeType != 0 && timeType != 1 && timeType != 2 && timeType != 3) {
            throw new ServiceException(ErrorCode.QUERY_ALARM_TYPE_RADIO_ERROR_BY_TYPE);
        } else {
            alarmTypeItemDtos = alarmInfoMapper.getAlarmTypeRatioByType(timeType);

            if (alarmTypeItemDtos != null) {
                AlarmTypeRatioRespDto alarmTypeRatioRespDto = new AlarmTypeRatioRespDto();
                List<DicItem> dicItems = dicItemMapper.queryDicItemList("alarm_event");
                Map<String, AlarmTypeItemDto> map = alarmTypeItemDtos
                        .stream()
                        .collect(Collectors.toMap(AlarmTypeItemDto::getAlarmCode, it -> it));
                dicItems.forEach(it -> {
                    if (!map.containsKey(it.getItemCode())) {
                        AlarmTypeItemDto alarmTypeItemDto = new AlarmTypeItemDto();
                        alarmTypeItemDto.setAlarmCode(it.getItemCode());
                        alarmTypeItemDto.setAlarmName(it.getItemName());
                        alarmTypeItemDto.setCount(0);
                        retDtos.add(alarmTypeItemDto);
                    } else {
                        AlarmTypeItemDto alarmTypeItemDto = map.get(it.getItemCode());
                        alarmTypeItemDto.setAlarmName(it.getItemName());
                        retDtos.add(alarmTypeItemDto);
                    }
                });
                /** 求和总人数 */
                int sum = retDtos.stream().mapToInt(item -> item.getCount() == null ? 0 : item.getCount())
                        .sum();
                DecimalFormat df = new DecimalFormat("#0.00");//保留小数点后两位
                retDtos.forEach(item -> {
                    Double percentage = (double) item.getCount() / sum * 100;
//                    item.setRadio(percentage.intValue());
                    item.setRadio(Double.parseDouble(df.format(percentage)));
                });
                List<String> existEventCodes = alarmTypeItemDtos.stream().map(AlarmTypeItemDto::getAlarmCode)
                        .collect(Collectors.toList());
                List<AlarmTypeItemDto> collect = alarm_event.stream().filter(event -> {
                    return !existEventCodes.contains(event.getItemCode());
                }).map(item -> {
                    AlarmTypeItemDto alarmTypeItemDto = new AlarmTypeItemDto();
                    alarmTypeItemDto.setAlarmCode(item.getItemCode());
                    alarmTypeItemDto.setAlarmName(item.getItemName());
                    alarmTypeItemDto.setCount(0);
                    alarmTypeItemDto.setRadio(0);
                    return alarmTypeItemDto;
                }).collect(Collectors.toList());
                alarmTypeItemDtos.addAll(collect);
                alarmTypeItemDtos.sort(Comparator.comparing(AlarmTypeItemDto::getAlarmCode));
                alarmTypeRatioRespDto.setAlarmTypeItemDtos(alarmTypeItemDtos);
                return alarmTypeRatioRespDto;
            } else {
                return new AlarmTypeRatioRespDto();
            }
        }
    }

    @Override
    public AlarmCountRespDto getAlarmCountByType(int timeType) {
        List<AlarmCountItemDto> alarmCountItemDtos = new ArrayList<>();
        Date now = new Date();
        if (timeType == 1) {
            alarmCountItemDtos = alarmInfoMapper.getAlarmCountByHour(now);
        } else if (timeType == 2) {
            alarmCountItemDtos = alarmInfoMapper.getAlarmCountByDay(now);
        } else if (timeType == 3) {
            alarmCountItemDtos = alarmInfoMapper.getAlarmCountByMonth(now);
        } else {
            throw new ServiceException(ErrorCode.QUERY_ALARM_COUNT_ERROR_BY_TYPE);
        }
        AlarmCountRespDto alarmCountRespDto = new AlarmCountRespDto();
        alarmCountRespDto.setAlarmCountItemDtos(alarmCountItemDtos);
        return alarmCountRespDto;
    }

    @Override
    public PageInfo<AlarmInfo> multiQryAlarmList(AlarmQryInfo alarmQryInfo) {
        int pageNo = alarmQryInfo.getPageNo();
        int pageSize = alarmQryInfo.getPageSize();
        PageMethod.startPage(pageNo, pageSize);
        List<AlarmInfo> list = alarmInfoMapper.multiQryAlarmList(alarmQryInfo);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setEventName(mapping.get(e.getEvent()));
                e.setAreaName(e.getArea());
                e.setResourceTypeName(mapping.get(e.getResourceType()));
                e.setAlgCodeName(mapping.get(e.getAlgCode()));
                e.setAlarmType(dicItemMapper.getAlarmType(e.getEvent()));
                if (Strings.isEmpty(e.getAuditStatus())) {
                    e.setAuditStatus("none");
                    e.setStatus(1);
                }
            });
        }
        return new PageInfo<>(list);
    }

    @Override
    public List<AlarmInfo> getExportAlarmList(AlarmQryInfo alarmQryInfo) {
        List<AlarmInfo> list = alarmInfoMapper.multiQryAlarmList(alarmQryInfo);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setEventName(mapping.get(e.getEvent()));
                e.setAreaName(mapping.get(e.getArea()));
                e.setResourceTypeName(mapping.get(e.getResourceType()));
            });
        }
        return list;
    }

    @Override
    public StatisticalCountRespDto statisticalCount() {
        return alarmInfoMapper.statisticalCount();
    }

    @Override
    public PageListRespDto pageList(PageListReqDto pageListReqDto) {
        PageHelper.startPage(pageListReqDto.getPageNo(), pageListReqDto.getPageSize());
        List<AlarmInfo> alarmInfos = alarmInfoMapper.pageList(pageListReqDto);
        List<PageListItemDto> pageListItemDtos = new ArrayList<>();
        if (alarmInfos != null && alarmInfos.size() > 0) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            alarmInfos.forEach(item -> {
                PageListItemDto pageListItemDto = new PageListItemDto();
                pageListItemDto.setId(item.getId());
                pageListItemDto.setEventName(mapping.get(item.getEvent()));
                pageListItemDto.setResourceId(item.getResourceId());
                pageListItemDto.setResourceName(item.getResourceName());
                pageListItemDto.setAreaName(mapping.get(item.getArea()));
                pageListItemDto.setAlarmTime(item.getCatchTime());
                pageListItemDto.setProcessFlag(item.getProcessFlag());
                pageListItemDto.setStatus(item.getStatus());
                pageListItemDto.setCatchImageUrl(fileServicePath + item.getCatchImageUrl());
                pageListItemDtos.add(pageListItemDto);
            });
            PageListRespDto pageListRespDto = new PageListRespDto();
            PageInfo<AlarmInfo> pageInfo = new PageInfo<>(alarmInfos);
            pageListRespDto.setCount(pageInfo.getTotal());
            pageListRespDto.setPageCount(pageInfo.getPages());
            pageListRespDto.setList(pageListItemDtos);
            return pageListRespDto;
        } else {
            return new PageListRespDto();
        }
    }

    @Override
    public AlarmInfo getAlarmInfo(Integer alarmInfoId) {
        AlarmInfo po = alarmInfoMapper.getById(alarmInfoId);
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        po.setEventName(mapping.get(po.getEvent()));
        po.setAreaName(mapping.get(po.getArea()));
        po.setResourceTypeName(mapping.get(po.getResourceType()));
        return po;
    }

    @Override
    public AlarmDetailRespDto queryDetailById(Integer id) {
        AlarmInfo alarmInfo = alarmInfoMapper.queryDetailById(id);
        if (ObjectUtil.isEmpty(alarmInfo)) {
            throw new ServiceException(ErrorCode.ALARM_INFO_IS_EMPTY);
        }
        AlarmDetailRespDto alarmDetailRespDto = ObjectCovertUtils.doToDto(alarmInfo, AlarmDetailRespDto.class);
        alarmDetailRespDto.setCatchImageUrl(fileServicePath + alarmInfo.getCatchImageUrl());
        alarmDetailRespDto.setEventCode(alarmInfo.getEvent());
        alarmDetailRespDto.setVioArea(alarmInfo.getArea());
        alarmDetailRespDto.setAlarmType(dicItemMapper.getAlarmType(alarmInfo.getEvent()));
        List<DicItem> district_type = dicItemMapper.queryDicItemListByDicCode("district_type");
        Optional<DicItem> first2 = district_type.stream().filter(it -> it.getItemCode().equals(alarmDetailRespDto.getVioArea())).findFirst();
        if (first2.isPresent()) {
            alarmDetailRespDto.setVioArea(first2.get().getItemName());
        }
        alarmDetailRespDto.setEventName(dicItemMapper.queryItemNameByItemCode(alarmInfo.getEvent()));
        alarmDetailRespDto.setProcessFlag(alarmInfo.getProcessFlag());

        List<AlarmViolationsDetailInfo> alarmViolationsDetailInfos = alarmInfoMapper.queryViolationsInfo(id);
        Optional<AlarmViolationsDetailInfo> first = alarmViolationsDetailInfos.stream().findFirst();
        AlarmObjectDto alarmObjectDto = new AlarmObjectDto();

        //处理违规对象信息
        if (first.isPresent()) {
            AlarmViolationsDetailInfo violationsDetailInfo = first.get();
            switch (violationsDetailInfo.getType()) {
                case 1: /**1：违规车辆*/
                    alarmObjectDto = alarmInfoMapper.queryCarVioByAlarmId(id);
                    List<DicItem> car_type = dicItemMapper.queryDicItemListByDicCode("car_type");
                    AlarmObjectDto finalAlarmObjectDto = alarmObjectDto;
                    Optional<DicItem> first1 = car_type.stream().filter(it -> it.getItemCode().equals(finalAlarmObjectDto.getType())).findFirst();
                    if (first1.isPresent()) {
                        alarmObjectDto.setType(first1.get().getItemName());
                    }
                    if (Strings.isEmpty(alarmObjectDto.getAlarmObjName())
                            && Strings.isNotEmpty(alarmObjectDto.getAlarmObjId())) {
                        alarmObjectDto.setAlarmObjName(alarmObjectDto.getAlarmObjId());
                    }
                    if (Strings.isEmpty(alarmObjectDto.getType())) {
                        alarmObjectDto.setType("未知");
                    }

                    break;
                case 2: /**2：违规人员3*/
                    alarmObjectDto = alarmInfoMapper.queryPersonVioByAlarmId(id);
                    List<DicItem> person_type = dicItemMapper.queryDicItemListByDicCode("person_type");
                    AlarmObjectDto finalAlarmObjectDto_p = alarmObjectDto;
                    Optional<DicItem> pe1 = person_type.stream().filter(it -> it.getItemCode().equals(finalAlarmObjectDto_p.getType())).findFirst();
                    if (pe1.isPresent()) {
                        alarmObjectDto.setType(pe1.get().getItemName());
                    }
                    break;
                case 3: /**3.违规楼栋*/
                    alarmObjectDto = alarmInfoMapper.queryBuildingByAlarmId(id);
                    List<DicItem> build_type = dicItemMapper.queryDicItemListByDicCode("building_type");
                    AlarmObjectDto finalAlarmObjectDto_b = alarmObjectDto;
                    Optional<DicItem> pe2 = build_type.stream().filter(it -> it.getItemCode().equals(finalAlarmObjectDto_b.getType())).findFirst();
                    if (pe2.isPresent()) {
                        alarmObjectDto.setType(pe2.get().getItemName());
                    }
                    break;
            }
            Integer curCnt = alarmInfoMapper.queryVioCnt(id, alarmObjectDto.getAlarmObjId(),
                    new Date(), DateUtils.addDays(new Date(), 1));
            Integer totalCnt = alarmInfoMapper.queryVioCnt(id, alarmObjectDto.getAlarmObjId(),
                    null, null);
            alarmObjectDto.setCurDayViolCnt(curCnt);
            alarmObjectDto.setTotalVioCnt(totalCnt);
            alarmDetailRespDto.setViolationsType(violationsDetailInfo.getType());
        } else {
            switch (alarmInfo.getEvent()) {
                case "jdcltlfsj"://机动车乱停乱放
                case "jdcblrxrsj"://机动车不礼让行人
                    alarmDetailRespDto.setViolationsType(1);
                    break;
                case "zgdlgsj"://主干道遛狗
                case "wgfysj"://翻阅围栏
                    alarmDetailRespDto.setViolationsType(2);
                    break;
                case "gkpwsj"://高空抛物
                    alarmDetailRespDto.setViolationsType(3);
                    break;
            }
        }
        alarmDetailRespDto.setTargetObject(alarmObjectDto);
        //处理违规处理步骤信息
        List<AlarmDealInfoRspDto> alarmDealInfoRspDtos = alarmInfoMapper.queryAlarmStatusByAlarmId(id);
        alarmDealInfoRspDtos.forEach(it -> {
            if (it.getStatus().equals("3")) {//1:未核实;2:未处置;3:处置中;4:已处置
                it.setStatus("处置中");
            }
            if (it.getStatus().equals("1")) {
                it.setStatus("未核实");
            }
            if (it.getStatus().equals("2")) {
                it.setStatus("未处置");
            }
            if (it.getStatus().equals("4")) {
                it.setStatus("已处置");
            }
        });
        alarmDetailRespDto.setDeals(alarmDealInfoRspDtos);
        return alarmDetailRespDto;
    }

    @Override
    public PropertyPersonListDto queryPropertyPersonList() {
        List<PropertyPersonItemDto> propertyPersonItemDtos = personInfoMapper.queryPropertyPerson();
        if (propertyPersonItemDtos != null && propertyPersonItemDtos.size() > 0) {
            PropertyPersonListDto propertyPersonListDto = new PropertyPersonListDto();
            propertyPersonListDto.setPropertyPersonItemDtos(propertyPersonItemDtos);
            return propertyPersonListDto;
        } else {
            return new PropertyPersonListDto();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithoutChecking(UpdateWithoutCheckingReqDto updateWithoutCheckingReqDto) {
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        String eventDesc = mapping.get(updateWithoutCheckingReqDto.getEvent());
//        if (ObjectUtil.isEmpty(eventDesc)) {
//            throw new ServiceException(ErrorCode.ALARM_EVENT_NOT_ESIXT);
//        }
        AlarmInfo update = new AlarmInfo();
        update.setId(updateWithoutCheckingReqDto.getId());
        // update.setEvent(updateWithoutCheckingReqDto.getEvent());
        update.setEventDesc(updateWithoutCheckingReqDto.getEventDesc());
        //update.setUpdateUser(updateWithoutCheckingReqDto.getUserName());
        if (updateWithoutCheckingReqDto.getIsReal() == 1) {
            update.setStatus(2);
            update.setAuditStatus("valid");
        } else {
            update.setStatus(4);
            update.setAuditStatus("invalid");
        }
        alarmInfoMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotDisposal(UpdateNotDisposalReqDto updateNotDisposalReqDto) {

        Integer dealType = updateNotDisposalReqDto.getDealType();
        AlarmInfo update = new AlarmInfo();
        update.setId(updateNotDisposalReqDto.getId());
        update.setEventDesc(updateNotDisposalReqDto.getEventDesc());
        update.setStatus(updateNotDisposalReqDto.getDealStatus());//更新告警信息表的状态


        //告警信息处置信息添加一行记录
        Date now = new Date();
        List<AlarmDealInfo> alarmDealInfos = new ArrayList<>();
        AlarmDealInfo add = new AlarmDealInfo();
        add.setIssuedTime(now);
        add.setDealPerson(updateNotDisposalReqDto.getUserId());
        add.setCreateTime(now);
        add.setDeleted(0);
        add.setAlarmId(updateNotDisposalReqDto.getId());
        add.setDealPersonName(updateNotDisposalReqDto.getUserName());
        add.setDealDesc(updateNotDisposalReqDto.getEventDesc());
        add.setDealStatus(update.getStatus());
        alarmDealInfos.add(add);
        alarmDealInfoMapper.batchInsert(alarmDealInfos);

        alarmInfoMapper.updateById(update);
    }

    @Override
    public FlashingRespDto queryCount() {
        return alarmInfoMapper.queryCount();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AlarmInfo dealAlarmInfo(DealAlarmInfoReqDto dealAlarmInfoReqDto) throws Exception {
        AlarmInfo add = new AlarmInfo();
        String data = dealAlarmInfoReqDto.getData();
        String path = "pc/event/" + dealAlarmInfoReqDto.getPath();
        LOGGER.info("算法调度返回:====> {}", data);
        LOGGER.info("roi返回:====>" + Arrays.deepToString(dealAlarmInfoReqDto.getRoi()));
        /** 根据resourceId查询经度 纬度 告警区域*/
        MonitorDevicePO monitorDevicePO = monitorDeviceMapper.getById(dealAlarmInfoReqDto.getResourceId());
        if (ObjectUtil.isEmpty(monitorDevicePO)) {
            throw new ServiceException(ErrorCode.DEVICE_ID_EMPTY);
        }

        /** 根据算法的不同处理逻辑不同 暂时不处理 */
        add.setResourceId(dealAlarmInfoReqDto.getResourceId());
        add.setResourceName(monitorDevicePO.getDeviceName());
        add.setEvent(dealAlarmInfoReqDto.getEventCode());
        add.setStatus(1);//默认为未核实
        if (ObjectUtil.isNotEmpty(dealAlarmInfoReqDto.getCatchTime())) {
            try {
                add.setCatchTime(DateUtils.parseDate(dealAlarmInfoReqDto.getCatchTime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                add.setCatchTime(new Date());
            }
            ;
        }
        add.setArea(monitorDevicePO.getDistrict());
        add.setCatchImageUrl(path);
        add.setProcessFlag(1);
        add.setCreateTime(new Date());
        add.setDeleted(0);
        add.setResourceType("device_report");
        add.setLatitude(monitorDevicePO.getDeviceLatitude());
        add.setLongitude(monitorDevicePO.getDeviceLongitude());
        alarmInfoMapper.insert(add);

        /** 添加违法对象 */
        //HashMap<String,Integer> vios = new HashMap<>();
        //解析违规对象，这里利用了之前的data这个字段
        if (data != null) {
            HashMap<String, Integer> vios = JSONUtil.toBean(data, new TypeReference<HashMap<String, Integer>>() {
            }, false);
            addViolationsPerson(add.getId(), vios);
        }
        LOGGER.debug("实时告警信息新增成功");

//        Timer timer = new Timer(); // 创建计时器对象
//        int delayInMillis = 1000 * 60 * uploadDelay; // 设置延迟时间(2分钟）
//        TimerTask task = new TimerTask() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                //获取并上传录像文件
//                uploadRecordToMinio(path, dealAlarmInfoReqDto.getResourceId(), dealAlarmInfoReqDto.getCatchTime());
//            }
//        };
//        timer.schedule(task, delayInMillis);

        return add;
    }

    @Override
    public FlashingRespDto queryAlarmCntByTime(Integer time) {
        Integer totalByTime = this.alarmInfoMapper.getTotalByTime(time);
        FlashingRespDto dto = new FlashingRespDto();
        dto.setCount(totalByTime);
        return dto;

    }

    @Override
    public List<PersonCarAlarmItemDto> getPeronCarAlarmCountByType(String reportType, Date beginDate, Date endDate, Integer vioType) {
        List<PersonCarAlarmItemDto> countByType = this.alarmInfoMapper.getPeronCarAlarmCountByType(reportType, vioType, beginDate, endDate);
        return countByType;
    }


    public Integer diffMonth(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);

        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        return month;
    }

    @Override
    public List<AlarmTableRspDto> getAlarmTableByTimeType(Integer timeType, String reportType, Date beginDate, Date endDate) {
        String format = "%Y-%m-%d";
        if (beginDate != null && endDate != null && diffMonth(beginDate, endDate) > 3) {//说明是要按年统计
            format = "%Y-%m";
        }

        List<AlarmTableDto> alarmTableByTimeType = this.alarmInfoMapper.getAlarmTableByTimeType(reportType, format, beginDate, endDate);
        Map<String, List<AlarmTableDto>> collect = alarmTableByTimeType.stream()
                .collect(Collectors.groupingBy(AlarmTableDto::getCatchTime, Collectors.toList()));
        List<AlarmTableRspDto> rspDtos = new ArrayList<>();
        collect.forEach((k, v) -> {
            AlarmTableRspDto dto = new AlarmTableRspDto();
            dto.setCatchTime(k);
            dto.setDtos(v);
            rspDtos.add(dto);
        });

        List<DicItem> alarm_event = dicItemHandler.getAllDicItemByDicCode("alarm_event");
        Map<String, DicItem> eventDict = alarm_event.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        rspDtos.forEach(it -> {
            List<String> matches = new ArrayList<>();
            it.getDtos().forEach(item -> {
                if (eventDict.containsKey(item.getEventCode())) {
                    item.setEventName(eventDict.get(item.getEventCode()).getItemName());
                    matches.add(item.getEventCode());
                }
            });
            //补齐
            eventDict.forEach((k, v) -> {
                if (!matches.contains(k)) {
                    AlarmTableDto dto = new AlarmTableDto();
                    dto.setCatchTime(it.getCatchTime());
                    dto.setCnt(0);
                    dto.setEventCode(k);
                    dto.setEventName(v.getItemName());
                    it.getDtos().add(dto);
                }
            });

        });
        //算平均
        AlarmTableRspDto aveTableRsp = new AlarmTableRspDto();
        aveTableRsp.setCatchTime("平均");
        Map<String, AlarmTableDto> dtoMap = new HashMap<>();
        rspDtos.forEach(it -> {
            it.getDtos().forEach(item -> {
                if (!dtoMap.containsKey(item.getEventCode())) {
                    AlarmTableDto dto = new AlarmTableDto();
                    dto.setCatchTime("平均");
                    dto.setCnt(0);
                    dto.setEventCode(item.getEventCode());
                    dto.setEventName(item.getEventName());
                    dtoMap.put(item.getEventCode(), dto);
                }
                AlarmTableDto tmpDto = dtoMap.get(item.getEventCode());
                tmpDto.setCnt(tmpDto.getCnt() + item.getCnt());//算总数
            });
        });

        List<AlarmTableRspDto> sortedRsp = rspDtos.stream().sorted(Comparator.comparing(AlarmTableRspDto::getCatchTime))
                .collect(Collectors.toList());
        int size = dtoMap.size();
        if (size > 0) {
            List<AlarmTableDto> collect1 = dtoMap.values().stream().map(it -> {
                Integer total = it.getCnt();
                it.setCnt(total / size);
                return it;
            }).collect(Collectors.toList());
            aveTableRsp.setDtos(collect1);
            sortedRsp.add(aveTableRsp);
        }
        return sortedRsp;
    }

    @Override
    public AreaAlarmCountRespDto getAreaAlarmCountByType2(String resourceType, Date beginDate, Date endDate) {
        List<AreaItemDto> areaItemDtos = alarmInfoMapper.getAreaAlarmCountByType2(resourceType, beginDate, endDate);
        if (areaItemDtos != null) {
            List<DicItem> dicItems = dicItemMapper.queryDicItemList("district_type");
            if (dicItems != null && dicItems.size() > 0) {
                AreaAlarmCountRespDto areaAlarmCountRespDto = new AreaAlarmCountRespDto();
                if (areaItemDtos.size() != dicItems.size()) {
                    List<String> source = new ArrayList<>();
                    dicItems.forEach(item -> {
                        source.add(item.getItemCode());
                    });
                    List<String> target = new ArrayList<>();
                    for (AreaItemDto areaItemDto : areaItemDtos) {
                        areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                        target.add(areaItemDto.getAreaCode());
                    }
                    source.removeAll(target);
                    for (String areaCode : source) {
                        AreaItemDto areaItemDto = new AreaItemDto();
                        areaItemDto.setAreaCode(areaCode);
                        areaItemDto.setAreaCount(0);
                        areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                        areaItemDtos.add(areaItemDto);
                    }

                } else {
                    for (AreaItemDto areaItemDto : areaItemDtos) {
                        areaItemDto.setAreaName(dicItemMapper.queryItemNameByItemCode(areaItemDto.getAreaCode()));
                    }
                }
                List<AreaItemDto> collect = areaItemDtos.stream()
                        .sorted(Comparator.comparing(AreaItemDto::getAreaCount)
                                .reversed())
                        .collect(Collectors.toList());
                areaAlarmCountRespDto.setAreaItemDtos(collect.subList(0, 5));
                return areaAlarmCountRespDto;
            } else {
                return new AreaAlarmCountRespDto();
            }
        } else {
            return new AreaAlarmCountRespDto();
        }
    }

    @Override
    public AlarmTypeRatioRespDto getAlarmTypeRatioByType2(String resourceType, Date beginDate, Date endDate) {
        List<AlarmTypeItemDto> alarmTypeItemDtos = new ArrayList<>();
        List<AlarmTypeItemDto> retDtos = new ArrayList<>();
        List<DicItem> alarm_event = this.dicItemHandler.getAllDicItemByDicCode("alarm_event");
        alarmTypeItemDtos = alarmInfoMapper.getAlarmTypeRatioByType2(resourceType, beginDate, endDate);
        if (alarmTypeItemDtos != null) {
            AlarmTypeRatioRespDto alarmTypeRatioRespDto = new AlarmTypeRatioRespDto();
            List<DicItem> dicItems = dicItemMapper.queryDicItemList("alarm_event");
            Map<String, AlarmTypeItemDto> map = alarmTypeItemDtos
                    .stream()
                    .collect(Collectors.toMap(AlarmTypeItemDto::getAlarmCode, it -> it));
            dicItems.forEach(it -> {
                if (!map.containsKey(it.getItemCode())) {
                    AlarmTypeItemDto alarmTypeItemDto = new AlarmTypeItemDto();
                    alarmTypeItemDto.setAlarmCode(it.getItemCode());
                    alarmTypeItemDto.setAlarmName(it.getItemName());
                    alarmTypeItemDto.setCount(0);
                    retDtos.add(alarmTypeItemDto);
                } else {
                    AlarmTypeItemDto alarmTypeItemDto = map.get(it.getItemCode());
                    alarmTypeItemDto.setAlarmName(it.getItemName());
                    retDtos.add(alarmTypeItemDto);
                }
            });
            /** 求和总人数 */
            int sum = retDtos.stream().mapToInt(item -> item.getCount() == null ? 0 : item.getCount())
                    .sum();
            retDtos.forEach(item -> {
                Double percentage = (double) item.getCount() / sum * 100;
                item.setRadio(percentage.intValue());
            });
            List<String> existEventCodes = alarmTypeItemDtos.stream().map(AlarmTypeItemDto::getAlarmCode)
                    .collect(Collectors.toList());
            List<AlarmTypeItemDto> collect = alarm_event.stream().filter(event -> {
                return !existEventCodes.contains(event.getItemCode());
            }).map(item -> {
                AlarmTypeItemDto alarmTypeItemDto = new AlarmTypeItemDto();
                alarmTypeItemDto.setAlarmCode(item.getItemCode());
                alarmTypeItemDto.setAlarmName(item.getItemName());
                alarmTypeItemDto.setCount(0);
                alarmTypeItemDto.setRadio(0);
                return alarmTypeItemDto;
            }).collect(Collectors.toList());
            alarmTypeItemDtos.addAll(collect);
            alarmTypeItemDtos.sort(Comparator.comparing(AlarmTypeItemDto::getAlarmCode));
            alarmTypeRatioRespDto.setAlarmTypeItemDtos(alarmTypeItemDtos);
            return alarmTypeRatioRespDto;
        }
        return new AlarmTypeRatioRespDto();
    }

    @Override
    public AlarmCountRespDto getAlarmCountByTimeType(String resourceType, Date beginDate, Date endDate) {
        String format = "%Y-%m-%d";
        if (beginDate != null && endDate != null && endDate.getMonth() - beginDate.getMonth() > 1) {//说明是要按年统计
            format = "%Y-%m";
        }
        List<AlarmCountItemDto> alarmCountByTimeType = this.alarmInfoMapper.getAlarmCountByTimeType(resourceType, format, beginDate, endDate);
        AlarmCountRespDto alarmCountRespDto = new AlarmCountRespDto();
        alarmCountRespDto.setAlarmCountItemDtos(alarmCountByTimeType);
        return alarmCountRespDto;
    }

    /**
     * 添加违法事件对应违法对象，该对象可能为人，可能为车辆，可能为高空抛物楼栋信息。
     * 后期将其修改只关注对象id和对象类型,field2,field3相关的字段保留，但是没有任何作用。
     * <targetId,Type></targetId,Type>
     *
     * @param alarmId
     * @param userIds
     */
    private void addViolationsPerson(Integer alarmId, Map<String, Integer> userIds) {
        if (userIds.size() > 0) {
            Date date = new Date();
            List<AlarmViolationsDetailInfo> alarmViolationsDetailInfos = new ArrayList<>();
            userIds.forEach((k, v) -> {
                AlarmViolationsDetailInfo alarmViolationsDetailInfo = new AlarmViolationsDetailInfo();
                alarmViolationsDetailInfo.setAlarmId(alarmId);
                alarmViolationsDetailInfo.setCreateTime(date);
                alarmViolationsDetailInfo.setField1(k);
                alarmViolationsDetailInfo.setField2(null);
                alarmViolationsDetailInfo.setField3(null);
                if (v == 3) {//如果是3，要进行转换，从设备转换为楼栋
                    MonitorDevicePO byId = monitorDeviceMapper.getById(k);
                    alarmViolationsDetailInfo.setField1(String.valueOf(byId.getBuildingId()));
                }
                alarmViolationsDetailInfo.setType(v); //违规类型（1：违规车辆;2：违规人员;3.违规楼栋）
                alarmViolationsDetailInfos.add(alarmViolationsDetailInfo);
            });
            alarmViolationsDetailInfoMapper.insertBatch(alarmViolationsDetailInfos);
        }
    }

    /**
     * 处理原始图片
     *
     * @param oriImage
     * @return
     */
    private String dealImage(String oriImage, Integer[][] roi, List<Integer> json, List<String> keys) throws IOException {
        if (!ImageUtils.checkImage(oriImage)) {
            throw new ServiceException(ErrorCode.IMAGE_ERROR);
        }
        /** 绘制区域范围 */
        String roiImageStr = drawRoi(roi, oriImage);
        List<Integer> data = json;
        String[] base = roiImageStr.split(",");
        byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(base[base.length - 1]);
        byte[] image = draw(decode, data, "jpeg");
        return Base64.encode(image);
    }


    /***
     * 绘制图片区域
     * @param imageByte
     * @param points
     * @param imageFormatter
     * @return
     * @throws IOException
     */
    public byte[] draw(byte[] imageByte, List<Integer> points, String imageFormatter) throws IOException {
        if (points == null || points.size() == 0) {
            return imageByte;
        }
        // TODO 存在多个识别区域 【position [935,298,155,260]】 每4个点为一组
        List<List<Integer>> morePoints = Lists.partition(points, 4);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte));
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        //设置线条宽度
        graphics.setStroke(new BasicStroke(8));
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("微软雅黑", Font.ITALIC, 20));
        morePoints.forEach(f -> {
            //格式点坐标x轴  y轴 宽度 高度
            graphics.drawRect(f.get(0), f.get(1), f.get(2), f.get(3));
        });
        //关闭绘制
        graphics.dispose();
        //将绘制后的图片获取字节
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, imageFormatter, out);
        byte[] bytes = out.toByteArray();
        if (out != null) {
            IOUtils.closeQuietly(out);
        }
        return bytes;
    }

    private String drawList(List<AnalysisData> analysisDataList, String roiImageStr) {
        byte[] decode = Base64.decode(roiImageStr);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(decode));
            Graphics2D graphics2D = Graphics2dUtil.getGraphics2D(bufferedImage, Color.RED);
            analysisDataList.forEach(item -> {
                Integer[] face_bbox = item.getFace_bbox();
                Integer[] person_bbox = item.getPerson_bbox();
                Integer[] bbox = item.getBbox();

                if (ObjectUtil.isNotEmpty(face_bbox)) {
                    graphics2D.drawRect(face_bbox[0], face_bbox[1], face_bbox[2], face_bbox[3]);
                }

                if (ObjectUtil.isNotEmpty(person_bbox)) {
                    graphics2D.drawRect(person_bbox[0], person_bbox[1], person_bbox[2], person_bbox[3]);
                }

                if (ObjectUtil.isNotEmpty(bbox)) {
                    graphics2D.drawRect(bbox[0], bbox[1], bbox[2], bbox[3]);
                }
                /**
                 * 高空抛物
                 */
                Integer[][] bboxes = item.getBboxes();
                Long[] timestamps = item.getTimestamps();
                if (ObjectUtil.isNotNull(bboxes)) {
                    for (int i = 0; i < bboxes.length; i++) {
                        Integer[] temp = bboxes[i];
                        graphics2D.drawRect(temp[0], temp[1], temp[2], temp[3]);
                    }
                }
            });
            graphics2D.dispose();
            return getFinalImg(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 绘制范围区域
     *
     * @param roi
     * @param image
     * @return
     */
    private String drawRoi(Integer[][] roi, String image) {
        byte[] decode = Base64.decode(image);
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decode));
            Graphics graphics = bufferedImage.getGraphics();

            graphics.setFont(new Font("微软雅黑", Font.ITALIC, 20));
            graphics.setColor(Color.YELLOW);

            for (int i = 0; i < roi.length; i++) {
                List<Integer> xPoints = new ArrayList<>();
                List<Integer> yPoints = new ArrayList<>();
                for (int j = 0; j < roi[i].length; j++) {
                    if (j % 2 == 0) {
                        xPoints.add(roi[i][j]);
                    } else {
                        yPoints.add(roi[i][j]);
                    }
                }
                graphics.drawPolygon(xPoints.stream().mapToInt(Integer::valueOf).toArray(), yPoints.stream().mapToInt(Integer::valueOf).toArray(), xPoints.size());
                graphics.drawString("roi", xPoints.get(0), yPoints.get(0));
            }
            return getFinalImg(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将bufferedImage转化为base64字符串
     *
     * @param bufferedImage
     * @return
     */
    private String getFinalImg(BufferedImage bufferedImage) {
        return ImgUtil.toBase64(bufferedImage, "jpg");
    }

    /**
     * 上传事件录像文件到minio
     *
     * @param resourceId
     * @param
     * @param
     * @return
     * @throws Exception
     */
    private void uploadRecordToMinio(String imgUrl, String resourceId, String alarmTime) throws Exception {
        RecordParam param = new RecordParam();
        Date date = DateUtils.parseDate(alarmTime, "yyyy-MM-dd HH:mm:ss");
        long curTime = date.getTime();
        Date fromTime = new Date(curTime - fromTimeDelay * 1000);
        Date toTime = new Date(curTime + toTimeDelay * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String deviceSipId = monitorDeviceMapper.getSipIdByDeviceId(resourceId);//sipID
        param.setRoom(deviceSipId);
        param.setFromtime(sdf.format(fromTime));
        param.setTotime(sdf.format(toTime));
        JSONObject object = JSONUtil.parseObj(param);

        HutoolHttpClient client = new HutoolHttpClient();
        HttpResponse response = client.httpGet(downloadUrl, object);
        // 获取输入流对象
        InputStream inputStream = response.bodyStream();
        int available = inputStream.available();

        String filePath = "";
        if (available < 1024) {//过滤无效文件
            return;
        }
        filePath = videoManager.writeRecordToMinio(inputStream);//上传录像

        //录像文件路径写入数据表
        Integer alarmId = alarmInfoMapper.getAlarmIdByImageUrl(imgUrl);
        AlarmInfo update = new AlarmInfo();
        update.setId(alarmId);
        update.setRecordUrl("pc/event/" + filePath);
        update.setUpdateTime(null);
        alarmInfoMapper.updateById(update);
    }

}