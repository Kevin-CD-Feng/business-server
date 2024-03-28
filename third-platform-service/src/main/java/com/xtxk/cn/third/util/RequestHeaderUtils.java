package com.xtxk.cn.third.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.xtxk.cn.third.exception.ServiceException;
import com.xtxk.cn.third.common.PageReqDto;
import com.xtxk.cn.third.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author Administrator
 * @Description
 * @Version V3.0
 * @Since 1.0
 * @Date 2021/10/25
 */
public abstract class RequestHeaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeaderUtils.class);
    /**
     * 设置请求头
     * @return
     */
    public static HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    /**
     * 解析区域湖json结构体
     * @param jsonObject json对象
     * @return
     */
    public static JSONArray parseJsonData(JSONObject jsonObject) {
        JSONObject resultData = (JSONObject) jsonObject.get("resultData");
        if(ObjectUtil.isNull(resultData)){
            throw new ServiceException(ErrorCode.ERROR,"数据银行返回为空!!!");
        }
        return resultData.getJSONArray("content");
    }

    /**
     * 计算当前页及条数
     * @param jsonObject
     * @return
     */
    public static PageReqDto parsePage(JSONObject jsonObject) {
        try {
            JSONObject resultData = (JSONObject) jsonObject.get("resultData");
            // 当前页
            Integer pageNo = resultData.getInt("number");
            //条数
            Integer pageSize = resultData.getInt("size");
            //总数
            Integer total = resultData.getInt("totalElements");
            if (total != null && total > 0) {
                Integer page = pageNo + 1;
                if (page * pageSize > total) {
                    Integer number = total - page * pageSize;
                    if (number > 0) {
                        return new PageReqDto(page, number, total);
                    }
                } else {
                    return new PageReqDto(page, pageSize, total);
                }
            }
        } catch (Exception e) {
            logger.error("解析分页参数出错：" + e.getMessage());
        }
        return new PageReqDto();
    }

    /***
     * url
     * @param regionUrl
     * @param page
     * @param size
     * @return
     */
    public static String appendUrl(String regionUrl, Integer page, Integer size) {
        return new StringBuffer().append(regionUrl).append("&page=")
                .append(page).append("&size=")
                .append(size).toString();
    }

}
