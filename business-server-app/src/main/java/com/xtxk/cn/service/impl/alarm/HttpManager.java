package com.xtxk.cn.service.impl.alarm;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.utils.http.HttpResponse;
import com.xtxk.cn.utils.http.HutoolHttpClient;
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

    private void call(JSONObject jsonReq,String path,String desc){
        HttpResponse response = null;
        try {
            response = client.postJson(baseUrl + path, jsonReq);
            LOGGER.debug(JSONUtil.toJsonStr(response));
        } catch (Exception e) {
            LOGGER.error(desc+"{}",e);
        }
    }

}
