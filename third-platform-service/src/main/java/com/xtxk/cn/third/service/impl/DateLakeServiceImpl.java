package com.xtxk.cn.third.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.third.configuration.DataLakeConfiguration;
import com.xtxk.cn.third.config.CacheConfig;
import com.xtxk.cn.third.service.DateLakeService;
import com.xtxk.cn.third.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-22 14:30
 */
@Component
public class DateLakeServiceImpl implements DateLakeService, CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DateLakeServiceImpl.class);
    private static final String DATASYNC_ACCESS_TOKEN = "DATASYNC_ACCESS_TOKEN";

    @Autowired
    private CacheConfig cacheConfig;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DataLakeConfiguration lakeConfiguration;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getToken() {
        String token = findToken();
        if (StringUtils.isBlank(token)) {
            token = requestToken();
            logger.info("DATASYNC_ACCESS_TOKEN :{}", token);
            // token 有效期30d
            Integer expire = 60 * 24 * 30 - 60;
            if (StringUtils.isNotBlank(token)) {
                redisUtils.setStringExpire(DATASYNC_ACCESS_TOKEN, token, expire);
                cacheConfig.put(DATASYNC_ACCESS_TOKEN, token, expire, TimeUnit.MINUTES);
            } else {
                logger.error("获取DATASYNC_ACCESS_TOKEN 失败:{}", token);
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


    /**
     * 请求token
     */
    private String requestToken() {
        String requestTokenUrl = lakeConfiguration.getTokenUrl();
        logger.info("==============================token请求地址：" + requestTokenUrl);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(requestTokenUrl, String.class, String.class);
            logger.info("================token[responseEntity]================> " + JSONUtil.toJsonStr(responseEntity));
            if (ObjectUtil.isNotNull(responseEntity)) {
                JSONObject jsonObject = JSONUtil.toBean(responseEntity.getBody(), JSONObject.class);
                if (jsonObject != null) {
                    String token = jsonObject.getStr("access_token");
                    return token;
                }
            }
        } catch (Exception e) {
            logger.error("token请求失败： " + e.getMessage());
        }
        return null;
    }

    private String findTokenByRedis() {
        try {
            return redisUtils.getString(DATASYNC_ACCESS_TOKEN);
        } catch (Exception e) {
            logger.error("=======================从redis获取Token失败！" + e.getMessage());
            return findTokenByMemory();
        }
    }

    /***
     * 内存获取token
     * @return
     */
    private String findTokenByMemory() {
        Object token = cacheConfig.get(DATASYNC_ACCESS_TOKEN);
        if (ObjectUtil.isNull(token)) {
            return null;
        } else {
            return String.valueOf(token);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        String token = requestToken();
        if (StringUtils.isBlank(token)) {
            // token 有效期30d
            Integer expire = 60 * 24 * 30 - 60;
            if (StringUtils.isNotBlank(token)) {
                redisUtils.setStringExpire(DATASYNC_ACCESS_TOKEN, token, expire);
                cacheConfig.put(DATASYNC_ACCESS_TOKEN, token, expire, TimeUnit.MINUTES);
            } else {
                logger.error("获取DATASYNC_ACCESS_TOKEN 失败:{}", token);
            }
        }
        cacheConfig.infinite();
    }


}
