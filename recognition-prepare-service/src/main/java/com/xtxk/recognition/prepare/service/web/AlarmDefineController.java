package com.xtxk.recognition.prepare.service.web;

import cn.hutool.json.JSONUtil;
import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.*;
import com.xtxk.recognition.prepare.service.svc.AlarmDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = {"告警事件定义表模块"}, description = "告警事件定义表管理")
@RestController
@RequestMapping("/alarmDefine")
@AspectLogger
public class AlarmDefineController {
    @Autowired
    private AlarmDefineService alarmDefineService;

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public CommonResponse add(@Valid @RequestBody AddAlarmDefineReqDto addAlarmDefineReqDto) {
        alarmDefineService.add(addAlarmDefineReqDto);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "查询根据id详情")
    @PostMapping(value = "/detail")
    public CommonResponse<DetailAlarmDefineRespDto> detail(@Valid @RequestBody DetailAlarmDefineReqDto detailAlarmDefineReqDto) {
        DetailAlarmDefineRespDto detailAlarmDefineRespDto = alarmDefineService.detail(detailAlarmDefineReqDto.getId());
        return CommonResponse.success(detailAlarmDefineRespDto);
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public CommonResponse update(@Valid @RequestBody UpdateAlarmDefineReqDto updateAlarmDefineReqDto) {
        alarmDefineService.update(updateAlarmDefineReqDto);
        return CommonResponse.success("更新成功");
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public CommonResponse delete(@Valid @RequestBody DeleteAlarmDefineReqDto deleteAlarmDefineReqDto) {
        alarmDefineService.delete(deleteAlarmDefineReqDto.getIds());
        return CommonResponse.success("删除成功");
    }

    @ApiOperation(value = "事件列表")
    @PostMapping(value = "/list")
    public CommonResponse<PageAlarmDefineRespDto> list(@Valid @RequestBody PageAlarmDefineReqDto pageAlarmDefineReqDto) {
        PageAlarmDefineRespDto pageAlarmDefineRespDto = alarmDefineService.pageList(pageAlarmDefineReqDto);
        return CommonResponse.success(pageAlarmDefineRespDto);
    }

    @ApiOperation(value = "类型列表")
    @PostMapping(value = "/typeList")
    public CommonResponse typeList() {
        return CommonResponse.success(alarmDefineService.queryEventTypeList());
    }

    @ApiOperation(value = "根据类型查询事件")
    @PostMapping(value = "/type2Event")
    public CommonResponse type2Event(@Valid @RequestBody QueryAlarmEventReqDto queryAlarmEventReqDto) {
        return CommonResponse.success(alarmDefineService.type2Event(queryAlarmEventReqDto.getType()));
    }
}
