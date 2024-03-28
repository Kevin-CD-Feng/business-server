package com.xtxk.cn.controller.device;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.common.PageRspDto;
import com.xtxk.cn.dto.geogra.AddMonitorDeviceReqDto;
import com.xtxk.cn.dto.geogra.UpdateMonitorDeviceReqDto;
import com.xtxk.cn.dto.monitor.BuildingWithDeviceReqDto;
import com.xtxk.cn.dto.monitor.BuildingWithDeviceRspDto;
import com.xtxk.cn.dto.monitor.DeviceAndGateStateRspDto;
import com.xtxk.cn.dto.monitor.MonitorBuildingWithDeviceReqDto;
import com.xtxk.cn.entity.MonitorDevicePO;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.monitor.MonitorDeviceService;
import com.xtxk.cn.utils.json.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"地理信息管理模块-监控设备"}, description = "监控设备")
@RestController
@RequestMapping("/monitorDevice")
@AspectLogger
@Slf4j
public class MonitorDeviceController {
    @Autowired
    private MonitorDeviceService monitorDeviceService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询监控设备")
    public CommonResponse<PageRspDto<MonitorDevicePO>> getMonitorDeviceList(
            @RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") @ApiParam(value = "当前页数") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "每页条数") Integer pageSize,
            @RequestParam(value = "deviceStateName", required = false) @ApiParam(value = "设备在线状态 0 在线 1离线") Integer deviceStateName,
            @RequestParam(value = "districtName", required = false) @ApiParam(value = "设备所属区域") String districtName) {
        PageInfo<MonitorDevicePO> pageInfo = monitorDeviceService.getMonitorDeviceList(keyWord, pageNum, pageSize,deviceStateName,districtName);
        return CommonResponse.success(new PageRspDto<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList()));
    }

    @ApiOperation(value = "新增监控设备")
    @PostMapping
    public CommonResponse addMonitorDevice(@Valid @RequestBody AddMonitorDeviceReqDto addMonitorDeviceReqDto) {
        monitorDeviceService.addMonitorDevice(addMonitorDeviceReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "修改监控设备信息")
    @PutMapping("/{deviceId}")
    public CommonResponse updateMonitorDevice(@PathVariable("deviceId") @ApiParam(value = "设备id", required = true) String deviceId, @Valid @RequestBody UpdateMonitorDeviceReqDto updateMonitorDeviceReqDto) {
       if (null == deviceId) {
            log.info("deviceId is null");
            throw new ServiceException(ErrorCode.DEVICE_ID_NULL);
        }
        monitorDeviceService.updateMonitorDevice(deviceId, updateMonitorDeviceReqDto);
        return CommonResponse.success("修改成功");
    }

    @ApiOperation(value = "删除监控设备信息")
    @DeleteMapping("/{deviceId}")
    public CommonResponse deleteMonitorDevice(@PathVariable("deviceId") @ApiParam(value = "设备id", required = true) String deviceId) {
        if (null == deviceId) {
            log.info("deviceId is null");
            throw new ServiceException(ErrorCode.DEVICE_ID_NULL);
        }
        monitorDeviceService.deleteMonitorDevice(deviceId);
        return CommonResponse.success("删除成功");
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出监控设备信息")
    public void exportMonitorDevice(@RequestParam(value = "keyWord", required = false) @ApiParam(value = "关键字") String keyWord,
                                    @RequestParam(value = "deviceStateName", required = false) @ApiParam(value = "设备在线状态 0 在线 1离线") Integer deviceStateName,
                                    @RequestParam(value = "districtName", required = false) @ApiParam(value = "设备所属区域") String districtName,
                                    HttpServletResponse response) {
        List<MonitorDevicePO> list = monitorDeviceService.getExportMonitorDeviceList(keyWord,deviceStateName,districtName);
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()
                    + "监控设备信息", "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream())
                    .head(MonitorDevicePO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("监控设备信息")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.ERROR, "导出报表异常");
        }
    }

    @GetMapping
    @ApiOperation(value = "查询单个监控设备")
    public CommonResponse<MonitorDevicePO> getMonitorDevice(@RequestParam(value = "monitorDeviceId") @ApiParam(value = "监控设备id", required = true) String monitorDeviceId) {
        return CommonResponse.success(monitorDeviceService.getMonitorDevice(monitorDeviceId));
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有监控设备")
    public CommonResponse<List<MonitorDevicePO>> getAllMonitorDevice() {
        return CommonResponse.success(monitorDeviceService.getAllMonitorDevice());
    }

    @GetMapping("/queryDeviceAndGateStatus")
    @ApiOperation(value = "查询所有设备的状态")
    public CommonResponse<List<DeviceAndGateStateRspDto>> queryDeviceAndGateStatus() {
        return CommonResponse.success(monitorDeviceService.queryDeviceAndGateStatus());
    }


    @PostMapping("/addBuildingWithDevice")
    @ApiOperation(value = "绑定设备到楼栋")
    public CommonResponse addBuildingWithDevice(@RequestBody BuildingWithDeviceReqDto deviceReqDto){
        MonitorDevicePO po = new MonitorDevicePO();
        po.setDeviceId(deviceReqDto.getDeviceId());
        po.setBuildingId(deviceReqDto.getBuildingId());
        this.monitorDeviceService.updateMonitorDevice(po);
        return CommonResponse.success("");
    }

    @GetMapping("/queryBuildingByDeviceId")
    @ApiOperation(value = "根据设备查楼栋")
    public CommonResponse<BuildingWithDeviceRspDto> queryBuildingByDeviceId(@RequestParam String deviceId){
        MonitorDevicePO monitorDevice = monitorDeviceService.getMonitorDevice(deviceId);
        BuildingWithDeviceRspDto dto = new BuildingWithDeviceRspDto();
        dto.setDeviceId(deviceId);
        dto.setBuildingId(monitorDevice.getBuildingId());
        return CommonResponse.success(dto);
    }

    @PostMapping("/addMonitorBuildingWithDevice")
    @ApiOperation(value = "绑定高空抛物设备监控楼栋")
    public CommonResponse addMonitorBuildingWithDevice(@RequestBody MonitorBuildingWithDeviceReqDto deviceReqDto){
        MonitorDevicePO po = new MonitorDevicePO();
        po.setDeviceId(deviceReqDto.getDeviceId());
        po.setMonitorBuildingId(deviceReqDto.getMonitorBuildingId());
        this.monitorDeviceService.updateMonitorDevice(po);
        return CommonResponse.success("");
    }

}
