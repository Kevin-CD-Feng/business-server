package com.xtxk.recognition.prepare.service.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.component.ICanPushAlarm;
import com.xtxk.recognition.prepare.service.component.annotation.AspectLogger;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.CanPushAlarmReqDto;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.CanPushAlarmRespDto;
import com.xtxk.recognition.prepare.service.dto.alarmDefine.PushAlarmInfoReqDto;
import com.xtxk.recognition.prepare.service.manager.HttpManager;
import com.xtxk.recognition.prepare.service.svc.AlarmDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

@Api(tags = {"告警推送接口"})
@RestController
@RequestMapping("/alarmInfo")
@AspectLogger
@Slf4j
public class AlarmInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoController.class);

    @Autowired
    private AlarmDefineService alarmDefineService;


    @Autowired
    private HttpManager httpManager;

    @ApiOperation(value = "推送告警信息")
    @PostMapping("/pushAlarmInfo")
    public CommonResponse pushAlarmInfo(@Valid @RequestBody PushAlarmInfoReqDto pushAlarmInfoReqDto) throws IOException {
        //画框
        this.alarmDefineService.dealAlarmInfo(pushAlarmInfoReqDto);
        //这里处理一下违规对象
        CanPushAlarmReqDto request = new CanPushAlarmReqDto();
        request.setData(pushAlarmInfoReqDto.getData());
        request.setResourceId(pushAlarmInfoReqDto.getResourceId());
        request.setEventCode(pushAlarmInfoReqDto.getEventCode());
        request.setFeatures(pushAlarmInfoReqDto.getCoff());
        HashMap<String, Integer> vioResource = this.alarmDefineService.getVioResource(request);
        //推送到业务服务处理告警信息
        JSONObject jsonReq = JSONUtil.createObj()
                .putOpt("resourceId", pushAlarmInfoReqDto.getResourceId())
                .putOpt("eventCode", pushAlarmInfoReqDto.getEventCode())
                .putOpt("roi", pushAlarmInfoReqDto.getRoi())
                .putOpt("catchTime", pushAlarmInfoReqDto.getCatchTime())
                .putOpt("path", pushAlarmInfoReqDto.getPath())
                .putOpt("data",JSONUtil.toJsonStr(vioResource));

        this.httpManager.notifyAlarmInfo(jsonReq);
        return CommonResponse.success("新增成功");
    }

    @ApiOperation(value = "根据传入的数据判断是否推送告警图片")
    @PostMapping("/canPushAlarm")
    public CommonResponse canPushAlarm(@Valid @RequestBody CanPushAlarmReqDto canPushAlarmReqDto){
        CanPushAlarmRespDto canPushAlarmRespDto = new CanPushAlarmRespDto();
        Boolean flag= this.alarmDefineService.canPushAlarm(canPushAlarmReqDto);
        canPushAlarmRespDto.setResult(flag);
        return CommonResponse.success(canPushAlarmRespDto);
    }
}