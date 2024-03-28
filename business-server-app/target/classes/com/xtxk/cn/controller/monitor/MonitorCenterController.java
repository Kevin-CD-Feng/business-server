package com.xtxk.cn.controller.monitor;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.AlarmTypeCountResult;
import com.xtxk.cn.dto.geogra.MonitorDeviceDto;
import com.xtxk.cn.entity.DirectoryInfo;
import com.xtxk.cn.entity.DistrictItem;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.service.directory.DirectoryService;
import com.xtxk.cn.service.monitor.MonitorCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = {"监控中心模块"}, description = "监控中心")
@RestController
@RequestMapping("/monitorCenter")
@AspectLogger
public class MonitorCenterController {

    @Autowired
    private MonitorCenterService monitorCenterService;

    @Autowired
    private DirectoryService directoryService;

    @ApiOperation(value = "获取监控列表")
    @GetMapping("/monitorDeviceList")
    public CommonResponse<List<MonitorDeviceDto>> getMonitorDeviceList() {
        return CommonResponse.success(monitorCenterService.getMonitorDeviceList());
    }

    @ApiOperation(value = "获取监控设备概况")
    @GetMapping("/deviceAlarmTypeCount")
    public CommonResponse<AlarmTypeCountResult> getDeviceAlarmTypeCount() {
        return CommonResponse.success(monitorCenterService.getDeviceAlarmTypeCount());
    }

    @ApiOperation(value = "获取组织结构目录")
    @GetMapping("/getDirectoryInfoList")
    public CommonResponse<List<DirectoryInfo>> getDirectoryInfoList() {
        return CommonResponse.success(directoryService.selectDirectoryList());
    }

    @ApiOperation(value = "根据组织ID获取设备列表")
    @GetMapping("/getDeviceListByDirectoryId")
    public CommonResponse<List<MonitorDevicePO>> getDeviceListByDirectoryId(@RequestParam(required = true) String directoryId) {
        return CommonResponse.success(monitorCenterService.getDeviceListByDirectoryId(directoryId));
    }

    @ApiOperation(value = "根据设备名称检索设备列表")
    @GetMapping("/searchDeviceListByName")
    public CommonResponse<List<MonitorDevicePO>> searchDeviceListByName(@RequestParam(required = false) String deviceName) {
        return CommonResponse.success(monitorCenterService.searchDeviceListByName(deviceName));
    }

    @ApiOperation(value = "查询全部区域列表")
    @GetMapping("/selectAllArea")
    public CommonResponse<List<DistrictItem>> selectAllArea() {
        return CommonResponse.success(directoryService.selectAllArea());
    }
}
