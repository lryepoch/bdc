package com.gtsc.config.aop;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.gtsc.entity.ApiOperatorLog;
import com.gtsc.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/12/12 14:19
 * @description TODO 接口日志切面
 */
@Aspect
@Component
public class ApiRequestLogAspect {
    private static Logger logger = LoggerFactory.getLogger(ApiRequestLogAspect.class);

    private ThreadLocal<ApiOperatorLog> logThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.gtsc.controller..*(..))")
    public void requestLog() {
    }

    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            long beginTime = System.currentTimeMillis();

            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String beanName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String uri = request.getRequestURI();
            String remoteAddr = IpUtils.getIpAddr(request);
            String sessionId = request.getSession().getId();
            String method = request.getMethod();
            String params = "";
            if ("post".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = argsArrayToString(paramsArray);
            } else {
                Map<?, ?> paramsMap = request.getParameterMap();
                params = JSON.toJSONString(paramsMap);
            }

            logger.debug("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; methodName=" + methodName + "; params=" + params);

            ApiOperatorLog optLog = new ApiOperatorLog();
            optLog.setBeanName(beanName);
            optLog.setMethodName(methodName);
            optLog.setParams(params != null ? params : "");
            optLog.setRemoteAddr(remoteAddr);
            optLog.setSessionId(sessionId);
            optLog.setUri(uri);
            optLog.setRequestTime(beginTime);

            logThreadLocal.set(optLog);

            logger.debug(JSON.toJSONString(optLog));
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }
    }

    /**
     * 请求参数拼装
     *
     * @param paramsArray 参数数组
     * @return String
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null) {
            for (Object object : paramsArray) {
                if (object instanceof MultipartFile) {
                    return ((MultipartFile) object).getOriginalFilename();
                }
            }
            params = JSON.toJSONString(paramsArray).toString();
        }
        return params.trim();
    }


    @AfterReturning(returning = "result", pointcut = "requestLog()")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，返回内容
            ApiOperatorLog optLog = logThreadLocal.get();

            optLog.setResult(result);
            long beginTime = optLog.getRequestTime();
            long requestTime = (System.currentTimeMillis() - beginTime);
            optLog.setRequestTime(requestTime);

            logger.info("请求地址 [" + optLog.getRemoteAddr() + "] 请求耗时 [" + optLog.getRequestTime() + "] 毫秒，请求路径 [" + optLog.getUri() + "] 方法 [" + optLog.getMethodName() + "] 方法参数 [" + optLog.getParams() + "]");
            logger.info("返回内容 : " + JSONUtil.toJsonStr(optLog));

            logThreadLocal.remove();
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
        }
    }



}
