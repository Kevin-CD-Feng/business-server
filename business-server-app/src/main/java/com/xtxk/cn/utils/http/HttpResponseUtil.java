package com.xtxk.cn.utils.http;

import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.common.CommonResponse;

import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author lost
 * @Description HttpResponseUtils
 * @Date create in 2022-10-9 9:10:34
 */
public class HttpResponseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseUtil.class);

    /**
     * 回写json数据
     *
     * @param httpServletResponse
     * @param errorCode
     */
    public static void writeJson(HttpServletResponse httpServletResponse, ErrorCode errorCode) {
        writeJson(httpServletResponse, errorCode, null);
    }

    /**
     * 回写json数据
     *
     * @param httpServletResponse
     * @param errorCode
     */
    public static void writeJson(HttpServletResponse httpServletResponse, ErrorCode errorCode, Object object) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = httpServletResponse.getWriter();
            if (object != null) {
                printWriter.write(JSONUtil.toJsonStr(CommonResponse.error(errorCode, object)));
            } else {
                printWriter.write(JSONUtil.toJsonStr(CommonResponse.error(errorCode)));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
    }
}