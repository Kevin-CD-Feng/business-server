package com.xtxk.cn.third.util;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtxk.cn.third.exception.ServiceException;
import com.xtxk.cn.third.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2022/4/20 16:42
 */
@Configuration
public class HttpUtils {

    @Autowired
    private RestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);


    /***
     * @param url 请求地址
     * @param token
     * @return
     */
    public String post(String url, String token) {
        HttpEntity requestEntity = new HttpEntity(new JSONObject(), RequestHeaderUtils.getHeaders(token));
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }


    /***
     * 有参数请求
     * @param url url
     * @param token
     * @param requestBody json
     * @return
     */
    public String post(String url, String token, String requestBody) {
        try {
            HttpEntity requestEntity = new HttpEntity(requestBody, RequestHeaderUtils.getHeaders(token));
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception ex) {
            logger.error("===============post 调用第三方接口异常 ===========> url: " + url + " requestBody :" + requestBody + " 错误信息：" + ex.getMessage());
            throw new ServiceException(ErrorCode.ERROR,ex.getMessage());
        }
        return null;
    }


    /***
     * 数据入湖
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    public <T> String put(String url, String token, List<T> requestBody) {
        try {
            //TODO 属性为NULL不序列化
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            HttpEntity<String> requestEntity = new HttpEntity(mapper.writeValueAsString(requestBody), RequestHeaderUtils.getHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            //LogUtil.LOG.error("===============put===========>调用第三方接口返回: url:" + response);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception ex) {
            logger.error("===============put===========>保存数据异常: url:" + ex.getMessage());
            throw new ServiceException(ErrorCode.ERROR,ex.getMessage());
        }
        return null;
    }

    /***
     * 更新状态数据
     * @param url
     * @param token
     * @param requestBody
     * @param <T>
     * @return
     */
    public <T> String put(String url, String token, String requestBody) {
        try {
            HttpEntity requestEntity = new HttpEntity(requestBody, RequestHeaderUtils.getHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception ex) {
            logger.error("===============put===========> url: " + url + " requestBody :" + requestBody + " 错误信息：" + ex.getMessage());
            throw new ServiceException(ErrorCode.ERROR,ex.getMessage());
        }
        return null;
    }


    /***
     * 区域湖数据修改
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    public <T> String patch(String url, String token, List<T> requestBody) {
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            HttpEntity<String> requestEntity = new HttpEntity(mapper.writeValueAsString(requestBody), RequestHeaderUtils.getHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, String.class);
            //LogUtil.LOG.error("===============patch===========>调用第三方接口返回: url:" + response);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception ex) {
            logger.error("===============patch===========>更新数据异常: url:" + ex.getMessage());
            throw new ServiceException(ErrorCode.ERROR,ex.getMessage());
        }
        return null;
    }

    /***
     * 区域湖设备数据删除
     * @param url
     * @param token
     * @param requestBody
     * @return
     */
    public <T> String delete(String url, String token, T requestBody) {
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            HttpEntity<String> requestEntity = new HttpEntity(mapper.writeValueAsString(requestBody), RequestHeaderUtils.getHeaders(token));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception ex) {
            logger.error("===============delete===========>删除数据异常: url:" + ex.getMessage());
            throw new ServiceException(ErrorCode.ERROR,ex.getMessage());
        }
        return null;
    }

    /***
     * 下载远程图片
     * @param fileUrl 远程图片地址
     * @return
     * @throws IOException
     */
    public byte[] download(String fileUrl) throws IOException {
        byte[] buf = new byte[1024];
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            inputStream = connection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            for (int len = 0; (len = bufferedInputStream.read(buf)) != -1; ) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.error("===============下载远程图片异常===========> url:{}, 错误信息：{}", fileUrl, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
        return null;
    }


}
