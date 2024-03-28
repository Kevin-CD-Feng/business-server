package com.xtxk.cn.service.impl.video;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.xtxk.cn.dto.monitor.ThridConfig;
import com.xtxk.cn.dto.video.Detail;
import com.xtxk.cn.dto.video.ResponseResult;
import com.xtxk.cn.dto.video.VideoReq;
import com.xtxk.cn.entity.AlarmPolicyConfiguration;
import com.xtxk.cn.entity.DeviceVideoRecord;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.enums.StatusEnum;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.mapper.AlarmPolicyConfigurationMapper;
import com.xtxk.cn.mapper.DeviceVideoRecordMapper;
import com.xtxk.cn.mapper.MonitorDeviceMapper;
import com.xtxk.cn.service.video.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-07 10:00
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ThridConfig thridConfig;
    @Autowired
    private MonitorDeviceMapper monitorDeviceMapper;
    @Autowired
    private DeviceVideoRecordMapper videoRecordMapper;
    @Autowired
    private AlarmPolicyConfigurationMapper policyConfigurationMapper;

    @Override
    @Retryable(value = ServiceException.class, maxAttempts = 3, backoff = @Backoff(delay = 10000, multiplier = 2))
    public Boolean startVideo(String resourceId, String resourceName,String deviceSipId) {
        boolean flag = startDeviceRecord(resourceId,deviceSipId);
        if (!flag) {
            throw new ServiceException(ErrorCode.ERROR, resourceId);
        }
        DeviceVideoRecord record = new DeviceVideoRecord();
        record.setDeviceId(resourceId);
        record.setDeviceName(resourceName);
        record.setStatus(1);
        record.setEnable(0);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return videoRecordMapper.insert(record) > 0;
    }

    @Async
    @Override
    @Retryable(value = ServiceException.class, maxAttempts = 3, backoff = @Backoff(delay = 10000, multiplier = 2))
    public Boolean statusRepay(VideoReq videoReq) {
        Detail detail = videoReq.getDetail();
        String deviceId = detail.getResourceId();
        String status = detail.getStatus();
        if (StatusEnum.FAIL.getCode().equals(status)) {
            AlarmPolicyConfiguration configuration = policyConfigurationMapper.queryById(deviceId);
            boolean flag = startDeviceRecord(configuration.getResourceId(), configuration.getWebCoordinate());
            if (!flag) {
                throw new ServiceException(ErrorCode.ERROR, deviceId);
            }
        }
        return true;
    }

    @Override
    public List<AlarmPolicyConfiguration> getAllDevice() {
        return policyConfigurationMapper.queryDevice();
    }

    @Override
    public List<DeviceVideoRecord> getFailRecord() {
        return videoRecordMapper.getFailRecord();
    }

    @Override
    public Boolean stopVideo(String resourceId) {
        return stopDeviceRecord(resourceId);
    }

    @Recover
    public Boolean recover(ServiceException e) {
        System.out.println("回调结果设备id:" + e.getMsg());
        String deviceId = e.getMsg();
        AlarmPolicyConfiguration configuration = policyConfigurationMapper.queryById(deviceId);
        if (configuration != null) {
            DeviceVideoRecord record = new DeviceVideoRecord();
            record.setDeviceId(configuration.getResourceId());
            record.setDeviceName(configuration.getEventCode());
            record.setStatus(0);
            record.setEnable(0);
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            videoRecordMapper.insert(record);
        }
        return true;
    }


    private boolean startDeviceRecord(String deviceId, String deviceSipId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userID", "videoRecordUser");
        jsonObject.put("deviceID", deviceId);
        jsonObject.put("deviceSIPID", deviceSipId);
        jsonObject.put("deviceType", 0);

        Map<String, String> body = new HashMap<>();
        body.put("endPoint", "/com.xtxk.resourcerecordservice.grpc.api.ResourceRecordService/StartDeviceRecord");
        body.put("service", "resource-record-service.media-platform:8080");
        body.put("param", jsonObject.toString());
        body.put("serviceKey", "dongbu");
        body.put("token", thridConfig.getOperatorToken());
        body.put("nodeId", "");

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, headers);
        LOGGER.info("请求录像服务开始录像:startDeviceRecord,地址：{},请求参数{}", thridConfig.getDatacenter(), JSONUtil.toJsonStr(httpEntity));
        ResponseEntity<ResponseResult> response = restTemplate.postForEntity(thridConfig.getDatacenter(), httpEntity, ResponseResult.class);
        LOGGER.info("请求录像服务开始录像返回结果,设备id：{},返回结果：{}", deviceId, JSONUtil.toJsonStr(response));
        if (response == null || response.getStatusCode() != HttpStatus.OK) {
            throw new ServiceException(ErrorCode.ERROR, response.getBody().getResponseDesc());
        }
        return response.getBody().getIsSuccess();
    }


    private Boolean stopDeviceRecord(String resourceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userID", "videoRecordUser");
        jsonObject.put("resourceID", resourceId);

        Map<String, String> body = new HashMap<>();
        body.put("endPoint", "/com.xtxk.resourcerecordservice.grpc.api.ResourceRecordService/StopDeviceRecord");
        body.put("service", "resource-record-service.media-platform:8080");
        body.put("param", jsonObject.toString());
        body.put("token", thridConfig.getOperatorToken());
        body.put("nodeId", "");
        body.put("serviceKey", "dongbu");

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ResponseResult> response = restTemplate.postForEntity(thridConfig.getDatacenter(), httpEntity, ResponseResult.class);
        LOGGER.info("请求录像服务开始录像:StopDeviceRecord,地址：{},请求参数{}", thridConfig.getDatacenter(), JSONUtil.toJsonStr(httpEntity));
        if (response == null || response.getStatusCode() != HttpStatus.OK || !response.getBody().getIsSuccess()) {
            throw new ServiceException(ErrorCode.ERROR, response.getBody().getResponseDesc());
        }
        DeviceVideoRecord record = new DeviceVideoRecord();
        record.setDeviceId(resourceId);
        record.setStatus(0);
        record.setEnable(1);
        record.setUpdateTime(new Date());
        videoRecordMapper.insert(record);
        return response.getBody().getIsSuccess();
    }


    @Override
    public List<DeviceVideoRecord> getNormalRecord() {
        return videoRecordMapper.getNormalRecord();
    }

    @Override
    public List<DeviceVideoRecord> getStopRecord() {
        return videoRecordMapper.getStopRecord();
    }

}
