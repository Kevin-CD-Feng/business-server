package com.xtxk.cn.utils.http;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @Author daiqing(daiqing @ xingtu.com)
 * @Description 定义http请求返回对象
 * @Date create in 2022-07-16 16:05
 * @Modified by
 */
public class HttpResponse {
    /**
     * http请求服务返回码
     */
    private int code;
    /**
     * http服务请求返回错误消息
     */
    private String error;

    private byte[] data;

    private String charset;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpResponse(byte[] data) {
        this(data, null);
    }

    public HttpResponse(byte[] data, String charset) {
        this.data = data;
        if (StrUtil.isBlank(charset)) {
            charset = CharsetUtil.UTF_8;
        }
        this.charset = charset;
    }

    public String getStrResult() {
        return StrUtil.str(data, charset);
    }

    public JSONObject getJsonResult() {
        JSONObject jsonObject = JSONUtil.parseObj(StrUtil.str(data, this.charset));
        return jsonObject;
    }
}
