package com.xtxk.recognition.prepare.service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;


@ApiModel(description = "返回数据")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommonResponse<T> {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功", required = true)
    private Boolean success;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", required = true)
    private String code;
    /**
     * 状态消息
     */
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;
    /**
     * 数据结果集
     */
    @ApiModelProperty(value = "返回数据列表", required = true)
    private T data;

    private CommonResponse() {
    }

    /**
     * @return CommonResponse
     */
    public static CommonResponse success() {
        CommonResponse result = new CommonResponse();
        result.success = true;
        result.code = ErrorCode.SUCCESS.getCode();
        result.msg = ErrorCode.SUCCESS.getDesc();
        return result;
    }

    /**
     * @param msg 错误信息
     * @return CommonResponse
     */
    public static CommonResponse success(String msg) {
        CommonResponse result = success();
        result.msg = msg;
        return result;
    }

    public static <T> CommonResponse success(T data) {
        CommonResponse result = success();
        result.data = data;
        return result;
        }

    public static <T> CommonResponse success(String msg, T data) {
        CommonResponse result = success();
            result.msg = msg;
            result.data = data;
            return result;
        }

    /**
    * @param errorCode 错误码
    * @return CommonResponse
    */
    public static CommonResponse error(ErrorCode errorCode) {
        CommonResponse result = new CommonResponse();
        result.success = false;
        result.code = errorCode.getCode();
        result.msg = errorCode.getDesc();
        return result;
    }

    public static <T> CommonResponse error(ErrorCode errorCode, T data) {
        CommonResponse result = new CommonResponse();
        result.success = false;
        result.code = errorCode.getCode();
        result.msg = errorCode.getDesc();
        result.data = data;
        return result;
    }

    public static CommonResponse error(ErrorCode errorCode, String msg) {
        CommonResponse result = new CommonResponse();
        result.success = false;
        result.code = errorCode.getCode();
        result.msg = errorCode.getDesc() + (StringUtils.isNotBlank(msg) ? ":" + msg : "");
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}