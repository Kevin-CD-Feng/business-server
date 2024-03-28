package com.xtxk.cn.service.impl.statusmonitorweb;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xtxk.cn.dto.statusMonitorWeb.QueryUsersByConditionsReqDto;
import com.xtxk.cn.dto.statusMonitorWeb.QueryUsersByConditionsRspDto;
import com.xtxk.cn.service.statusmonitorweb.StatusMonitorWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wululu(wululu1 @ xingtu.com)
 * @Description 与 运营监控平台交互
 * @Date create in 2022/11/7 10:18
 */
@Service
public class StatusMonitorWebServiceImpl implements StatusMonitorWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusMonitorWebServiceImpl.class);

    @Autowired
    private StatusMonitorWebProperty statusMonitorWebProperty;

    /**
     * post json
     * @param httpUrl
     * @param jsonObject
     * @return
     */
    private String postHttpRequest(String httpUrl, JSONObject jsonObject) {
        LOGGER.info("http request url:{}", httpUrl);
        HttpRequest httpRequest = HttpUtil.createGet(httpUrl);
        String body = JSONUtil.toJsonStr(jsonObject);
        httpRequest.body(body);
        LOGGER.info("http request params:{}", body);
        HttpResponse httpResponse = httpRequest.execute();
        String responseStr = httpResponse.body();
        LOGGER.info("http response:{}", responseStr);
        return responseStr;
    }

    @Override
    public QueryUsersByConditionsRspDto queryUsersByConditions(QueryUsersByConditionsReqDto queryUsersByConditionsReqDto) {
        //设置成指定的部门
        queryUsersByConditionsReqDto.getParam().setDepartmentID(statusMonitorWebProperty.getDepartmentId());
        queryUsersByConditionsReqDto.setToken(statusMonitorWebProperty.getToken());
        final String createUserTokenForWebUrl = statusMonitorWebProperty.getUrl() + "/data/datacenter";
        LOGGER.info("获取运营监控平台指定的用户:{}", JSONUtil.toJsonStr(queryUsersByConditionsReqDto));
        String responseStr = postHttpRequest(createUserTokenForWebUrl, JSONUtil.parseObj(queryUsersByConditionsReqDto));
        LOGGER.info("获取运营监控平台指定的用户:{}", responseStr);
        return JSONUtil.toBean(responseStr, QueryUsersByConditionsRspDto.class, true);
    }
}