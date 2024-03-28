package com.xtxk.cn.utils.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * JsonUtil
 *
 * @author chenzhi
 * @date 2022/10/31 10:22
 * @description
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    static ObjectMapper mapper = new ObjectMapper();

    private static final String MESSAGE_ERROR = "json转换错误";

    public static byte[] obj2byte(Object obj) {
        byte[] nll = null;
        try {
            return mapper.writeValueAsBytes(obj);

        } catch (JsonProcessingException e) {
            LOGGER.error(MESSAGE_ERROR, e);
        }
        return nll;
    }

    public static <T> String obj2Str(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            LOGGER.error(MESSAGE_ERROR, e);
        }
        return null;
    }

    public static <T> T str2Obj(byte[] bytes, Class<T> clazz) {
        try {
            return mapper.readValue(bytes, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error(MESSAGE_ERROR, e);
        } catch (IOException e) {
            LOGGER.error("字节读取错误", e);
        }
        return null;
    }

    public static <T> List<T> str2List(String json,Class<T> clazz){
    try {
        return JSON.parseArray(json,clazz);
    }catch (Exception e){
        LOGGER.error(MESSAGE_ERROR, e);
    }
    return Collections.emptyList();
    }

    public static <T,E> E objToOtherObj(T t,Class<E> clazz) throws JsonProcessingException {
        return JSON.parseObject(mapper.writeValueAsString(t),clazz);
    }
}
