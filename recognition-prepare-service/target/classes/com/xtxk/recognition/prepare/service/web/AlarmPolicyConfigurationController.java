package com.xtxk.recognition.prepare.service.web;


import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.AlarmPolicyConfiguration.*;
import com.xtxk.recognition.prepare.service.svc.AlarmPolicyConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Api(tags = {"告警策略配置模块"})
@RestController
@RequestMapping("/alarmPolicyConfiguration")
@AspectLogger
public class AlarmPolicyConfigurationController {
    @Autowired
    private AlarmPolicyConfigurationService alarmPolicyConfigurationService;

    @ApiOperation(value = "告警策略分页列表")
    @PostMapping(value = "/list")
    public CommonResponse<ListAlarmPolicyConfigurationRespDto> list(@Valid @RequestBody ListAlarmPolicyConfigurationReqDto listAlarmPolicyConfigurationReqDto){
        ListAlarmPolicyConfigurationRespDto listAlarmPolicyConfigurationRespDto = alarmPolicyConfigurationService.list(listAlarmPolicyConfigurationReqDto);
        return CommonResponse.success(listAlarmPolicyConfigurationRespDto);
    }

    @ApiOperation(value = "添加/修改告警策略")
    @PostMapping(value = "/addAndUpdate")
    public CommonResponse addAndUpdate(@Valid @RequestBody AddAlarmPolicyConfigurationReqDto addAlarmPolicyConfigurationReqDto){
        alarmPolicyConfigurationService.addAndUpdate(addAlarmPolicyConfigurationReqDto);
        return CommonResponse.success("配置成功");
    }

    @ApiOperation(value = "全部告警策略事件列表")
    @PostMapping(value = "/configurationList")
    public CommonResponse<ConfigurationListDto> configurationList(){
        return CommonResponse.success(alarmPolicyConfigurationService.configurationList());
    }

    @ApiOperation(value = "查询已配置策略设备列表")
    @GetMapping(value = "/getAlarmPolicyDeviceList")
    public CommonResponse<List<ListAlarmPolicyDeviceDto>> getAlarmPolicyDeviceList(@RequestParam(required = false) String deviceName){
        return CommonResponse.success(alarmPolicyConfigurationService.getAlarmPolicyDeviceList(deviceName));
    }

}