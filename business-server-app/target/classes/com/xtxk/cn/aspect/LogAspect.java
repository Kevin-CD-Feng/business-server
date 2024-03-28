package com.xtxk.cn.aspect;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("@within(com.xtxk.cn.annotation.AspectLogger) || @annotation(com.xtxk.cn.annotation.AspectLogger)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Object data = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getMethod().getParameterTypes());
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        //获取参数名称
        String[] parameters = parameterNameDiscoverer.getParameterNames(method);
        // 获取参数入参
        Object[] args = joinPoint.getArgs();
        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();
        //获取类名
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        // TODO 部分接口方法参数为空处理逻辑
        List<Object> filterArgs = Arrays.stream(args).filter(Objects::nonNull)
                .filter(f -> !(f instanceof HttpServletRequest
                        || f instanceof HttpServletResponse || f instanceof MultipartFile || f instanceof MultipartFile[] )).collect(Collectors.toList());

        if (filterArgs.size()>0 && parameters.length>0) {
            Map<String, Object> paramMap = IntStream.range(0, filterArgs.size())
                    .boxed().collect(Collectors.toMap(o -> parameters[o], filterArgs::get));;
            log.info("请求入参=>MethodName:{},参数;{}.",methodName, JSONUtil.toJsonStr(paramMap));
        }
        try {
            // 执行原方法
            data = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.error("执行出错,方法名：{},参数;{}.",methodName,throwable.getMessage() );
            throw new RuntimeException(throwable.getMessage());
        }
        if (data != null) {
            log.info("请求出参====>MethodName:{},参数;{}.",methodName, JSONUtil.toJsonStr(data));
        }
        return data;
    }
}