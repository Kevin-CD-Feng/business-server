package com.xtxk.recognition.prepare.service.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.xtxk.recognition.prepare.service.common.CommonResponse;
import com.xtxk.recognition.prepare.service.enums.ErrorCode;
import com.xtxk.recognition.prepare.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数异常处理
     *
     * @params: [exception]
     * @return:
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return methodArgumentNotValid(exception.getBindingResult());
    }

    /**
     * 参数异常统一返回处理
     *
     * @param bindingResult
     * @return
     */
    private CommonResponse methodArgumentNotValid(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors != null && allErrors.size() > 0) {
            return CommonResponse.error(ErrorCode.PARAMS_ERROR,allErrors.get(0).getDefaultMessage());
        }
        return CommonResponse.error(ErrorCode.PARAMS_ERROR);
    }

    /**
     * 注解@JsonFormat 格式异常 与全局异常冲突 属于内部子异常
     *
     * @params: [exception]
     * @return:
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error(exception.getMessage(), exception);
        //内部子异常
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) exception.getCause();
            return CommonResponse.error(ErrorCode.PARAMS_ERROR, ex.getValue() + "格式错误");
        }
        if (exception.getCause().getCause() != null && exception.getCause().getCause() instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) exception.getCause().getCause();
            if (serviceException.getErrorCode() == ErrorCode.PARAMS_INLEGAL) {
                return CommonResponse.error(ErrorCode.PARAMS_INLEGAL, serviceException.getMsg());
            }
        }
        return CommonResponse.error(ErrorCode.PARAMS_ERROR);
    }

    /**
     * 业务异常统一处理
     *
     * @params: [exception]
     * @return:
     */
    @ExceptionHandler(ServiceException.class)
    public CommonResponse serviceExcetion(ServiceException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return StringUtils.isBlank(exception.getMsg()) ? CommonResponse.error(exception.getErrorCode()) :
                CommonResponse.error(exception.getErrorCode(),exception.getMessage());
    }

    /**
     * 全局异常
     *
     * @params: [exception]
     * @return:
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse globalExcetion(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return CommonResponse.error(ErrorCode.ERROR);
    }
}