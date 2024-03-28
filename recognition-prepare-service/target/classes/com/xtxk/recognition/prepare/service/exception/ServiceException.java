package com.xtxk.recognition.prepare.service.exception;

import com.xtxk.recognition.prepare.service.enums.ErrorCode;


public class ServiceException extends RuntimeException {

    private ErrorCode errorCode;

    private String msg;

    /**
     * @param errorCode
     */
    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    /**
     * 存在子异常描述
     *
     * @param errorCode
     * @param msg
     */
    public ServiceException(ErrorCode errorCode, String msg) {
        super(errorCode.getDesc() + msg);
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
