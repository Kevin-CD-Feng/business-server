package com.xtxk.recognition.prepare.service.http;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;


public class HutoolHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HutoolHttpClient.class);

    public HttpResponse postJson(String url, JSONObject param){
        LOGGER.debug("hutool post json!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).body(p.toString()).execute());

    }

    public HttpResponse postJson(String url, JSONObject param, Map<String, String> headers) {
        LOGGER.debug("hutool post json!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).headerMap(headers, true).body(p.toString()).execute());

    }

    public HttpResponse postJson(String url, JSONObject param, Map<String, String> headers, Proxy proxy) {
        LOGGER.debug("hutool post json!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).headerMap(headers, true).setProxy(proxy).body(p.toString()).execute());
    }

    public HttpResponse postForm(String url, JSONObject param) {
        LOGGER.debug("hutool post form!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).form(p).execute());
    }

    public HttpResponse postForm(String url, JSONObject param, Map<String, String> headers) {
        LOGGER.debug("hutool post form!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).headerMap(headers, true).form(p).execute());
    }

    public HttpResponse postForm(String url, JSONObject param, Map<String, String> headers, Proxy proxy){
        LOGGER.debug("hutool post form!");
        return innerHttpRequest(url, param, (u, p) -> HttpUtil.createPost(u).headerMap(headers, true).setProxy(proxy).form(p).execute());
    }

    /**
     * 模板代码封装
     *
     * @param url     http请求路径
     * @param param   http请求参数
     * @param request 实际的http处理对象
     * @return
     */
    private HttpResponse innerHttpRequest(String url, JSONObject param, BiFunction<String, JSONObject, cn.hutool.http.HttpResponse> request) {
        Objects.requireNonNull(url, "请求URL不能为空！");
        LOGGER.debug("hutool client 请求URL： >> {}", url);
        LOGGER.debug("hutool client 请求参数： >> {}", param);
        // 如果请求参数为空，构建空的请求参数
        if (param == null) {
            param = new JSONObject();
        }
        try {
            cn.hutool.http.HttpResponse response = request.apply(url, param);
            HttpResponse result = new HttpResponse(response.bodyBytes());
            result.setCode(response.getStatus());
            LOGGER.debug("hutool client rec: >> {}", response);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("hutool http client request error!");
        }
    }

    public HttpResponse get(String url, JSONObject param) {
        return innerHttpRequest(url, param, (u, p) -> {
            String paramStr = MapUtil.join(p, "&", "=", false);
            return HttpUtil.createGet(u + "?" + paramStr).execute();
        });
    }
}
