package com.resource.common.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
* @description 接口日志类
* @author lryepoch
* @date 2020/12/12 14:38
*
*/
@Aspect
@Component
public class LogAspect {

    private static Object[] params = null;
    private static String event = null;

    private final static Logger logger = LogManager.getLogger("resource");

    @Pointcut("@annotation(com.resource.common.aop.Log)")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}", request.getRequestURL());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        params = joinPoint.getArgs();
        event = getEvent(joinPoint);
    }

    private String getEvent(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Log log = methodSignature.getMethod().getAnnotation(Log.class);
        return log.description();
    }


    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) throws IOException {
//        if (object != null) {
//            CommonResult commonResult = (CommonResult) object;
//            if (Integer.valueOf(0).equals(commonResult.getData())) {
//                throw new SystemException(ResultEnum.ERR.getCode(), "无效操作");
//            } else {
//                if (commonResult.getCode() == 200 && params != null) {
//
//                }
//            }
//        }
    }
}
