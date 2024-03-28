package com.xtxk.recognition.prepare.service.manager;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.recognition.prepare.service.http.HttpResponse;
import com.xtxk.recognition.prepare.service.http.HutoolHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HttpManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpManager.class);

    private HutoolHttpClient client = new HutoolHttpClient();

    @Value("${scheduling.baseUrl}")
    private String baseUrl;

    @Value("${scheduling.algorithm.open}")
    private String algorithmOpenPath;

    @Value("${scheduling.algorithm.close}")
    private String algorithmClosePath;

    @Value("${scheduling.identify.open}")
    private String identifyOpenPath;

    @Value("${scheduling.identify.close}")
    private String identifyClosePath;

    @Value("${busiapp.baseUrl}")
    private String busiBaseUrl;

    @Value("${busiapp.dealAlarmInfoUrl}")
    private String dealAlarmInfoUrl;

    @Value("${busiapp.startRecordUrl}")
    private String startRecordUrl;

    @Value("${busiapp.stopRecordUrl}")
    private String stopRecordUrl;


    @Async
    public void algorithmOpen(JSONObject jsonReq){
        call(jsonReq,algorithmOpenPath,"新增/更新算法调用异常:");
    }

    @Async
    public void algorithmClose(JSONObject jsonReq){
        call(jsonReq,algorithmClosePath,"关闭算法调用异常");
    }

    @Async
    public void identifyOpen(JSONObject jsonReq){
        call(jsonReq,identifyOpenPath,"开启识别算法调用异常:");
    }

    @Async
    public void identifyClose(JSONObject jsonReq){
        call(jsonReq,identifyClosePath,"关闭识别算法调用异常:");
    }

    @Async
    public void notifyAlarmInfo(JSONObject jsonReq){call2(jsonReq,dealAlarmInfoUrl,"通知业务层告警信息报错:");}

    @Async
    public void startRecordUrl(JSONObject jsonReq){call2(jsonReq,startRecordUrl,"通知业务层告警信息报错:");}

    @Async
    public void stopRecordUrl(JSONObject jsonReq){call2(jsonReq,stopRecordUrl,"通知业务层告警信息报错:");}


    private void call(JSONObject jsonReq,String path,String desc){
        HttpResponse response = null;
        try {
            response = client.postJson(baseUrl + path, jsonReq);
            LOGGER.debug(JSONUtil.toJsonStr(response));
        } catch (Exception e) {
            LOGGER.error(desc);
        }
    }

    private void call2(JSONObject jsonReq,String path,String desc){
        HttpResponse response = null;
        try {
            LOGGER.info("url:{},path:{},json:{}",busiBaseUrl,path,JSONUtil.toJsonStr(jsonReq));
            client.postJson(busiBaseUrl + path, jsonReq);
        } catch (Exception e) {
            LOGGER.error(desc);
        }
    }
}
