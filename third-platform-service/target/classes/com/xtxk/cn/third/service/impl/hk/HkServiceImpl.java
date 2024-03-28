package com.xtxk.cn.third.service.impl.hk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.xtxk.cn.third.configuration.HkConfiguration;
import com.xtxk.cn.third.dto.hk.*;
import com.xtxk.cn.third.config.CacheConfig;
import com.xtxk.cn.third.config.MinioConfig;
import com.xtxk.cn.third.mapper.HkHighParabolicDeviceRelMapper;
import com.xtxk.cn.third.service.HkService;
import com.xtxk.cn.third.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 10:40
 */
@Service
public class HkServiceImpl implements HkService, CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HkServiceImpl.class);
    private static final String HK_ACCESS_TOKEN = "HK_ACCESS_TOKEN";

    @Autowired
    private HkConfiguration hkConfiguration;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CacheConfig cacheConfig;
    @Autowired
    private MessageManager messageManager;

    /***
     * 海康高空抛物回调
     * @param detection
     * @return
     */
    @Override
    public boolean eventRcv(EventObjectsThrownDetection detection) {
        List<EventObjectsThrownDetection.ParamsDTO.EventsDTO> list = detection.getParams().getEvents();
        List<EventMessage> paramList = new ArrayList<>();
        for (EventObjectsThrownDetection.ParamsDTO.EventsDTO event : list) {
            EventObjectsThrownDetection.ParamsDTO.EventsDTO.DataDTO dataDTO = event.getData();
            // TODO 设备信息数据
            EventObjectsThrownDetection.ParamsDTO.EventsDTO.DataDTO.TargetAttrsDTO targetAttrsDTO = dataDTO.getTargetAttrs();
            String deviceId = targetAttrsDTO.getDeviceIndexCode();
            List<EventObjectsThrownDetection.ParamsDTO.EventsDTO.DataDTO.ObjectsThrownDetectionDTO> dataList = dataDTO.getObjectsThrownDetection();
            for (EventObjectsThrownDetection.ParamsDTO.EventsDTO.DataDTO.ObjectsThrownDetectionDTO obj:dataList) {
                EventObjectsThrownDetection.ParamsDTO.EventsDTO.DataDTO.ObjectsThrownDetectionDTO.ImageDTO imageDTO = obj.getImage();
                EventMessage objectDTO = new EventMessage();
                objectDTO.setTime(obj.getStartTime());
                objectDTO.setImgUrl(imageDTO.getResourcesContent());
                objectDTO.setResourceId(deviceId);
                paramList.add(objectDTO);
            }
        }
        return messageManager.batchPublish(paramList);
    }


    /***
     *
     * @return
     */
    @Override
    public boolean startSub() {
        return subAlarmInfo();
    }

    /***
     * 查询高空抛物订阅信息
     * @return
     */
    @Override
    public String subView() {
        String token = getToken();
        String subUrl = hkConfiguration.getEventSubViewUrl();
        Map<String, String> map = new HashMap<>(2);
        map.put("http://", subUrl);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("access_token", token);
        headerMap.put("Content-Type", "application/json");
        String result = ArtemisHttpUtil.doPostStringArtemis(map, null, null, null, "application/json", headerMap);
        return result;
    }

    /***
     * 停止订阅高空抛物订阅
     * @return
     */
    @Override
    public String stopSub() {
        String token = getToken();
        String subUrl = hkConfiguration.getStopEventSubUrl();
        Map<String, String> map = new HashMap<>(2);
        map.put("http://", subUrl);

        // TODO 高空抛物事件码
        EventSubParamBody body = new EventSubParamBody();
        body.setEventTypes(Arrays.asList(930335));
        String bodyStr = JSONUtil.toJsonStr(body);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("access_token", token);
        headerMap.put("Content-Type", "application/json");

        String result = ArtemisHttpUtil.doPostStringArtemis(map, bodyStr, null, null, "application/json", headerMap);
        logger.info("订阅HK高空抛物事件回调：{}", result);
        return result;
    }

    /***
     * 订阅海康高空抛物事件
     */
    public boolean subAlarmInfo() {
        String token = getToken();
        String subUrl = hkConfiguration.getEventSubUrl();
        Map<String, String> map = new HashMap<>(2);
        map.put("http://", subUrl);

        // TODO 高空抛物事件码
        EventSubParamBody body = new EventSubParamBody();
        body.setEventTypes(Arrays.asList(930335));
        body.setEventDest(hkConfiguration.getEventSubUrl());
        String bodyStr = JSONUtil.toJsonStr(body);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("access_token", token);
        headerMap.put("Content-Type", "application/json");

        String result = ArtemisHttpUtil.doPostStringArtemis(map, bodyStr, null, null, "application/json", headerMap);
        logger.info("订阅HK高空抛物事件回调：{}", result);
        HkTokenRes res = JSONUtil.toBean(result, HkTokenRes.class);
        if (ObjectUtil.isNotNull(res) && ObjectUtil.isNotNull(res.getCode())) {
            return res.getCode().equals("0");
        } else {
            return false;
        }
    }

    private String getToken() {
        String token = findToken();
        if (StringUtils.isBlank(token)) {
            token = requestToken();
            logger.info("HK_ACCESS_TOKEN :{}", token);
            // token 有效期12h
            Integer expire = 60 * 12 - 10;
            if (StringUtils.isNotBlank(token)) {
                redisUtils.setStringExpire(HK_ACCESS_TOKEN, token, expire);
                cacheConfig.put(HK_ACCESS_TOKEN, token, expire, TimeUnit.MINUTES);
            } else {
                logger.error("获取HK_ACCESS_TOKEN 失败:{}", token);
            }
        }
        return token;
    }

    private String findToken() {
        String token = findTokenByRedis();
        if (StringUtils.isNotBlank(token)) {
            return token;
        } else {
            return findTokenByMemory();
        }
    }

    private String findTokenByRedis() {
        try {
            return redisUtils.getString(HK_ACCESS_TOKEN);
        } catch (Exception e) {
            logger.error("=======================从redis获取Token失败！" + e.getMessage());
            return findTokenByMemory();
        }
    }

    private String findTokenByMemory() {
        Object token = cacheConfig.get(HK_ACCESS_TOKEN);
        if (ObjectUtil.isNull(token)) {
            return null;
        } else {
            return String.valueOf(token);
        }
    }

    /***
     * 获取海康token令牌
     * @return
     */
    private String requestToken() {
        String tokenUrl = hkConfiguration.getTokenUrl();
        ArtemisConfig artemisConfig = new ArtemisConfig();
        artemisConfig.setAppKey(hkConfiguration.getAppKey());
        artemisConfig.setAppSecret(hkConfiguration.getAppSecret());
        artemisConfig.setHost(hkConfiguration.getHost());
        Map<String, String> map = new HashMap<>(2);
        map.put("http://", tokenUrl);
        logger.info("请求海康参数地址：{}", tokenUrl);
        String result = ArtemisHttpUtil.doPostStringArtemis(map, null, null, null, "application/json", null);
        logger.info("海康认证token返回参数：{}", result);
        HkTokenRes res = null;
        if (StringUtils.isNotBlank(result)) {
            res = JSONUtil.toBean(result, HkTokenRes.class);
        }
        if (ObjectUtil.isNotNull(res) && ObjectUtil.isNotNull(res.getData())) {
            return res.getData().getAccess_token();
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        subAlarmInfo();
    }
}
