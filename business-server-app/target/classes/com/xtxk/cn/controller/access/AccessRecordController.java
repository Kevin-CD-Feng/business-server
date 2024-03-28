package com.xtxk.cn.controller.access;

import com.xtxk.cn.annotation.AspectLogger;
import com.xtxk.cn.common.CommonResponse;
import com.xtxk.cn.dto.accessRecord.AccessCarInRecordResult;
import com.xtxk.cn.dto.accessRecord.AccessCarOutRecordResult;
import com.xtxk.cn.dto.accessRecord.AccessPersonRecordResult;
import com.xtxk.cn.service.access.AccessRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"人车流量模块"}, description = "人车流量模块")
@RestController
@RequestMapping("/accessRecord")
@AspectLogger
public class AccessRecordController {
    @Autowired
    private AccessRecordService accessRecordService;

    @ApiOperation(value = "人员出入流量")
    @GetMapping("/person")
    public CommonResponse<AccessPersonRecordResult> getAccessPeopleRecordResult() {
        return CommonResponse.success(accessRecordService.getAccessPersonRecordResult());
    }

    @ApiOperation(value = "车辆流入数据")
    @GetMapping("/carIn")
    public CommonResponse<AccessCarInRecordResult> getAccessCarInRecordResult(@RequestParam(required = true) String date) {
        return CommonResponse.success(accessRecordService.getAccessCarInRecordResult(date));
    }

    @ApiOperation(value = "车辆流出数据")
    @GetMapping("/carOut")
    public CommonResponse<AccessCarOutRecordResult> getAccessCarOutRecordResult(@RequestParam(required = true) String date) {
        return CommonResponse.success(accessRecordService.getAccessCarOutRecordResult(date));
    }
}
