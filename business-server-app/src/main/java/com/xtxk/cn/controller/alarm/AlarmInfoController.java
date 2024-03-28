package com.xtxk.cn.controller.alarm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.configurer.minio.MinioConfig;
import com.xtxk.cn.configurer.websocket.WebSocketServer;
import com.xtxk.cn.dto.AlarmStatusCount;
import com.xtxk.cn.dto.SendToUser;
import com.xtxk.cn.dto.alarmInfo.*;
import com.xtxk.cn.dto.freemaker.CellEntity;
import com.xtxk.cn.dto.freemaker.ContentEntity;
import com.xtxk.cn.dto.freemaker.HeaderEntity;
import com.xtxk.cn.dto.freemaker.ImageEntity;
import com.xtxk.cn.entity.AlarmInfo;
import com.xtxk.cn.entity.GateMachinePO;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.AlarmViolationsDetailInfoMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.alarm.AlarmInfoManager;
import com.xtxk.cn.service.alarm.AlarmInfoService;
import com.xtxk.cn.service.alarm.DocumentManager;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"告警中心模块"}, description = "告警中心管理")
@RestController
@RequestMapping("/alarmInfo")
@AspectLogger
public class AlarmInfoController {
    @Autowired
    private AlarmInfoService alarmInfoService;

    @Autowired
    private AlarmInfoManager alarmInfoManager;

    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;

    @Autowired
    private AlarmViolationsDetailInfoMapper alarmViolationsDetailInfoMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private DocumentManager documentManager;

    @Autowired
    private MinioConfig minioConfig;


    @Value("${fileServicePath}")
    private String fileServicePath;

    @ApiOperation(value = "获取实时告警列表")
    @GetMapping("/latest")
    public CommonResponse<List<AlarmInfo>> getLatestAlarmInfo() {
        return CommonResponse.success(alarmInfoService.getLatestAlarmInfo());
    }

    @ApiOperation(value = "获取告警任务处置情况")
    @GetMapping("/dealSituation")
    public CommonResponse<List<AlarmStatusCount>> getAlarmDealSit(@RequestParam(value = "time", required = false, defaultValue = "0") @ApiParam(value = "时间，0:表示全部,1:当天 2:当月 3:当年") Integer time) {
        return CommonResponse.success(alarmInfoService.getAlarmDealSit(time));
    }

    @ApiOperation(value = "根据经纬度展示告警信息")
    @GetMapping("/showAlarmInfos")
    public CommonResponse showAlarmInfos() {
        Map<String, List<AlarmLimit3ItemDto>> alarmInfoMap = alarmInfoService.showAlarmInfos();
        return CommonResponse.success(alarmInfoMap);
    }

    @ApiOperation(value = "根据时间类型查询各区域告警数量")
    @PostMapping("/getAreaAlarmCountByType")
    public CommonResponse<AreaAlarmCountRespDto> getAreaAlarmCountByType(@Valid @RequestBody AreaAlarmCountReqDto areaAlarmCountReqDto) {
        AreaAlarmCountRespDto areaAlarmCountRespDto = alarmInfoService.getAreaAlarmCountByType(areaAlarmCountReqDto.getTimeType());
        return CommonResponse.success(areaAlarmCountRespDto);
    }

    @ApiOperation(value = "统计-查询各区域告警数量")
    @PostMapping("/getAreaAlarmCountByType2")
    public CommonResponse<AreaAlarmCountRespDto> getAreaAlarmCountByType2(@Valid @RequestBody AlarmTableReqDto alarmCountReqDto) {
        AreaAlarmCountRespDto areaAlarmCountRespDto = alarmInfoService
                .getAreaAlarmCountByType2(alarmCountReqDto.getReportType(), alarmCountReqDto.getBeginDate(), alarmCountReqDto.getEndDate());
        return CommonResponse.success(areaAlarmCountRespDto);
    }


    @ApiOperation(value = "根据时间类型查询告警类型比例")
    @PostMapping("/getAlarmTypeRatioByType")
    public CommonResponse<AlarmTypeRatioRespDto> getAlarmTypeRatioByType(@Valid @RequestBody AlarmTypeRatioReqDto alarmTypeRatioReqDto) {
        AlarmTypeRatioRespDto alarmTypeRatioRespDto = alarmInfoService.getAlarmTypeRatioByType(alarmTypeRatioReqDto.getTimeType());
        return CommonResponse.success(alarmTypeRatioRespDto);
    }

    @ApiOperation(value = "统计-查询告警类型比例")
    @PostMapping("/getAlarmTypeRatioByType2")
    public CommonResponse<AlarmTypeRatioRespDto> getAlarmTypeRatioByType2(@Valid @RequestBody AlarmTableReqDto alarmCountReqDto) {
        AlarmTypeRatioRespDto alarmTypeRatioRespDto = alarmInfoService
                .getAlarmTypeRatioByType2(alarmCountReqDto.getReportType(), alarmCountReqDto.getBeginDate(), alarmCountReqDto.getEndDate());
        return CommonResponse.success(alarmTypeRatioRespDto);
    }


    @ApiOperation(value = "根据时间类型查询告警数量")
    @PostMapping("/getAlarmCountByType")
    public CommonResponse<AlarmCountRespDto> getAlarmCountByType(@Valid @RequestBody AlarmCountReqDto alarmCountReqDto) {
        AlarmCountRespDto alarmCountRespDto = alarmInfoService.getAlarmCountByType(alarmCountReqDto.getTimeType());
        return CommonResponse.success(alarmCountRespDto);
    }


    @ApiOperation(value = "统计-查询告警数量")
    @PostMapping("/getAlarmCountByType2")
    public CommonResponse<AlarmCountRespDto> getAlarmCountByType2(@Valid @RequestBody AlarmTableReqDto alarmCountReqDto) {
        AlarmCountRespDto alarmCountRespDto = alarmInfoService
                .getAlarmCountByTimeType(alarmCountReqDto.getReportType(), alarmCountReqDto.getBeginDate(), alarmCountReqDto.getEndDate());
        return CommonResponse.success(alarmCountRespDto);
    }


    @ApiOperation(value = "根据时间类型查询人员车辆告警")
    @PostMapping("/getPeronCarAlarmCountByType")
    public CommonResponse<PersonCarAlarmCountRspDto> getPeronCarAlarmCountByType(@Valid @RequestBody PeronCarAlarmCountByTypeDto peronCarAlarmCountByTypeDto) {
        List<PersonCarAlarmItemDto> peronCarAlarmCountByType = this.alarmInfoService
                .getPeronCarAlarmCountByType(peronCarAlarmCountByTypeDto.getReportType(), peronCarAlarmCountByTypeDto.getBeginDate(),
                        peronCarAlarmCountByTypeDto.getEndDate(), 1);//查人
        List<PersonCarAlarmItemDto> peronCarAlarmCountByType2 = this.alarmInfoService
                .getPeronCarAlarmCountByType(peronCarAlarmCountByTypeDto.getReportType(), peronCarAlarmCountByTypeDto.getBeginDate(),
                        peronCarAlarmCountByTypeDto.getEndDate(), 2);//查车
        PersonCarAlarmCountRspDto dto = new PersonCarAlarmCountRspDto();
        dto.setAlarmCountItemPerson(peronCarAlarmCountByType);
        dto.setAlarmCountItemCar(peronCarAlarmCountByType2);
        return CommonResponse.success(dto);
    }

    @ApiOperation(value = "根据时间类型查询告警类型表格")
    @PostMapping("/getAlarmTableByTimeType")
    public CommonResponse<List<AlarmTableRspDto>> getAlarmTableByTimeType(@Valid @RequestBody AlarmTableReqDto alarmCountReqDto) {
        List<AlarmTableRspDto> alarmTableByTimeType = this.alarmInfoService
                .getAlarmTableByTimeType(1, alarmCountReqDto.getReportType(),
                        alarmCountReqDto.getBeginDate(), alarmCountReqDto.getEndDate());
        return CommonResponse.success(alarmTableByTimeType);
    }

    @PostMapping("/genReportPDF")
    @ApiOperation(value = "统计分析-导出PDF报表")
    public CommonResponse genAnalysReportPDF(@RequestBody AlarmTablePDFReqDto vo) {
        List<AlarmTableRspDto> alarmTable = this.alarmInfoService
                .getAlarmTableByTimeType(1, vo.getReportType(),
                        vo.getBeginDate(), vo.getEndDate());
//        List<AlarmTableRspDto> alarmTable = alarmTable_o.stream()
//                .filter(it -> !it.getCatchTime().equals("平均"))
//                .collect(Collectors.toList());
        AlarmTablePDFRspDto rspDto = new AlarmTablePDFRspDto();
        String baseDir = System.getProperty("user.dir") + "/template";
        List<HeaderEntity> headerEntities = new ArrayList<>();
        HeaderEntity dateHeader = HeaderEntity.builder().content("日期").build();
        headerEntities.add(dateHeader);
        Optional<AlarmTableRspDto> firstItem = alarmTable.stream().findFirst();
        firstItem.ifPresent(alarmTableRspDto -> alarmTableRspDto.getDtos().forEach(it -> {
            HeaderEntity e = HeaderEntity.builder().content(it.getEventName()).build();
            headerEntities.add(e);
        }));
        List<ImageEntity> imageEntities = vo.getFiles()
                .stream()
                .map(it -> {
                    String oriImages = it.getContent();
                    String[] base = oriImages.split(",");
                    return ImageEntity.builder().content(base[base.length - 1]).title(it.getTitle()).build();
                }).collect(Collectors.toList());
        List<ContentEntity> contentEntities = new ArrayList<>();
        alarmTable.forEach(it -> {
            ContentEntity contentEntity = ContentEntity.builder().items(new ArrayList<>()).build();
            CellEntity dateCol = CellEntity.builder().content(it.getCatchTime()).build();
            contentEntity.getItems().add(dateCol);
            it.getDtos().forEach(item -> {
                CellEntity cntCol = CellEntity.builder().content(String.valueOf(item.getCnt())).build();
                contentEntity.getItems().add(cntCol);
            });
            contentEntities.add(contentEntity);
        });
        Map<String, Object> tableData = new HashMap<>();
        tableData.put("imageList", imageEntities);
        tableData.put("tableHeader", headerEntities);
        tableData.put("tables", contentEntities);
        HashMap<String, String> hashMap = this.documentManager.generatorPDF(baseDir, tableData);
        if (hashMap.containsKey("pdfPath")) {
            String path = minioConfig.localUpload(hashMap.get("pdfPath"));
            if (Strings.isEmpty(path)) {
                return CommonResponse.error(ErrorCode.ERROR, "文件存储空间已满，请联系管理员扩展空间.");
            }
            rspDto.setPdf(fileServicePath + "pc/event/" + path);
            Thread t = new Thread(() -> {
                FileUtil.del(hashMap.get("wordPath"));
            });
            t.start();
            return CommonResponse.success(rspDto);
        }
        return CommonResponse.success(rspDto);
    }

    @ApiOperation(value = "获取告警事件列表")
    @GetMapping(value = "/page")
    public CommonResponse<List<AlarmInfo>> multiQryAlarmList(AlarmQryInfo alarmQryInfo) {
        PageInfo<AlarmInfo> pageInfo = alarmInfoService.multiQryAlarmList(alarmQryInfo);
        return CommonResponse.success(new PageRspDto<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList()));
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出告警事件信息")
    public void export(AlarmQryInfo alarmQryInfo, HttpServletResponse response) {
        List<AlarmInfo> list = alarmInfoService.getExportAlarmList(alarmQryInfo);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "告警事件信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(AlarmInfo.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("告警事件信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }


    @GetMapping("/statisticalCount")
    @ApiOperation(value = "查询各状态数量")
    public CommonResponse<StatisticalCountRespDto> statisticalCount() {
        StatisticalCountRespDto statisticalCountRespDto = alarmInfoService.statisticalCount();
        return CommonResponse.success(statisticalCountRespDto);
    }

    @ApiOperation(value = "告警中心-告警事件分页列表", hidden = true)
    @PostMapping("/pageList")
    public CommonResponse<PageListRespDto> pageList(@Valid @RequestBody PageListReqDto pageListReqDto) {
        PageListRespDto pageListRespDto = alarmInfoService.pageList(pageListReqDto);
        return CommonResponse.success(pageListRespDto);
    }

    @GetMapping
    @ApiOperation(value = "查询告警详情", hidden = true)
    public CommonResponse<GateMachinePO> getAlarmInfo(@RequestParam(value = "alarmInfoId") @ApiParam(value = "告警id", required = true) Integer alarmInfoId) {
        return CommonResponse.success(alarmInfoService.getAlarmInfo(alarmInfoId));
    }

    @ApiOperation(value = "告警中心-根据id查询告警详情")
    @PostMapping("/queryDetailById")
    public CommonResponse<AlarmDetailRespDto> queryDetailById(@Valid @RequestBody AlarmDetailReqDto alarmDetailReqDto) {
        AlarmDetailRespDto alarmDetailRespDto = alarmInfoService.queryDetailById(alarmDetailReqDto.getId());
        return CommonResponse.success(alarmDetailRespDto);
    }

    @ApiOperation(value = "物业人员列表")
    @GetMapping("/queryPropertyPersonList")
    @Deprecated
    public CommonResponse<PropertyPersonListDto> queryPropertyPersonList() {
        PropertyPersonListDto propertyPersonListDto = alarmInfoService.queryPropertyPersonList();
        return CommonResponse.success(propertyPersonListDto);
    }

    @ApiOperation(value = "告警中心-未核实事件更新")
    @PostMapping("/updateDetail")
    public CommonResponse updateWithoutChecking(@RequestBody UpdateWithoutCheckingReqDto updateWithoutCheckingReqDto) {
        alarmInfoService.updateWithoutChecking(updateWithoutCheckingReqDto);
        return CommonResponse.success("处理成功");
    }

    @ApiOperation(value = "告警中心-未处置事件更新")
    @PostMapping("/updateNotDisposal")
    public CommonResponse updateNotDisposal(@Valid @RequestBody UpdateNotDisposalReqDto updateNotDisposalReqDto) {
        updateNotDisposalReqDto.setDealType(1);
        updateNotDisposalReqDto.setProcessFlag(1);
        alarmInfoService.updateNotDisposal(updateNotDisposalReqDto);
        return CommonResponse.success("处理成功");
    }

    @ApiOperation(value = "事件管理-批量更新")
    @PostMapping("/batchUpdateNotDisposal")
    public CommonResponse BatchUpdateNotDisposal(@Valid @RequestBody List<UpdateNotDisposalReqDto> updateNotDisposalReqDtos) {
        updateNotDisposalReqDtos.forEach(it -> {
            it.setDealType(1);
            it.setProcessFlag(1);
            alarmInfoService.updateNotDisposal(it);
        });
        return CommonResponse.success("处理成功");
    }

    @ApiOperation(value = "告警中心-未核实和未处理数量查询")
    @PostMapping("/queryCount")
    public CommonResponse<FlashingRespDto> queryCount() {
        return CommonResponse.success(alarmInfoService.queryCount());
    }

    //业务系统收到预警的接口
    @ApiOperation(value = "处理告警信息")
    @PostMapping("/dealAlarmInfo")
    public CommonResponse<FlashingRespDto> dealAlarmInfo(@Valid @RequestBody DealAlarmInfoReqDto dealAlarmInfoReqDto) throws Exception {
        AlarmInfo info = alarmInfoManager.dealAlarmInfo(dealAlarmInfoReqDto);
        SendToUser<AlarmInfo> sendToUser = new SendToUser<>();
        sendToUser.setFuncName("informAlarmInfo");
        sendToUser.setData(new ArrayList<>());


        //Date catchTime = info.getCatchTime();
        sendToUser.getData().add(info);
        webSocketServer.sendToAll(JSONUtil.toJsonStr(sendToUser));
        return CommonResponse.success("处理成功");
    }


    @ApiOperation(value = "根据日月年获取告警数量")
    @GetMapping("/queryAlarmCntByTime")
    public CommonResponse<FlashingRespDto> queryAlarmCntByTime(@RequestParam Integer time) throws IOException {
        FlashingRespDto dto = this.alarmInfoService.queryAlarmCntByTime(time);
        return CommonResponse.success(dto);
    }


    @PostMapping("/pushResourceOnline")
    @ApiOperation("接收核心控制服务上线资源")
    public CommonResponse pushDeviceOnline(@RequestBody List<PushDeviceOnlineVo> pushDeviceOnlineVo) throws IOException {
        SendToUser<PushDeviceOnlineVo> sendToUser = new SendToUser<>();
        sendToUser.setFuncName("informDeviceStatus");
        sendToUser.setData(new ArrayList<>());
        List<String> deviceIdList = monitorDeviceMapper.getAllDeviceId();
        for (PushDeviceOnlineVo deviceOnlineVo : pushDeviceOnlineVo) {
            if (deviceOnlineVo.getResType().equals("Device") && deviceIdList.contains(deviceOnlineVo.getResId())) {
                sendToUser.getData().add(deviceOnlineVo);
                MonitorDevicePO devicePO = new MonitorDevicePO();
                devicePO.setDeviceId(deviceOnlineVo.getResId());
                devicePO.setDeviceState(0);
                //devicePO.setDeviceSipId(deviceOnlineVo.g);
                if (deviceOnlineVo.getStatus().equals("offline")) {
                    devicePO.setDeviceState(1);
                }
                this.monitorDeviceMapper.update(devicePO);

                //给前端推送设备上下线消息
                webSocketServer.sendToAll(JSONUtil.toJsonStr(sendToUser));
            }
        }

        return CommonResponse.success("");
    }

    @PostMapping("/updateBuildingViolationInfo")
    @ApiOperation("更新楼层")
    public CommonResponse updateBuildingViolationInfo(@RequestBody BuildingVioDto buildingVioDto) throws IOException {
        this.alarmViolationsDetailInfoMapper.updateBuildingViolationInfo(buildingVioDto.getFloorNumber(), buildingVioDto.getHouseNumber(), String.valueOf(buildingVioDto.getBuildingId()));
        return CommonResponse.success("更新楼层成功.");
    }


}