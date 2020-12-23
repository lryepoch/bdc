package com.gtsc.config.aop;

import cn.hutool.core.util.ArrayUtil;
import com.gtsc.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author
 * @date
 * @描述 定时任务限定IP AOP
 */
@Aspect
@Configuration
public class LimitIpAspect {
    Logger logger = LoggerFactory.getLogger(LimitIpAspect.class);

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.gtsc.config.aop.LimitIp)")
    public void limitIp() {
    }

    /**
     * @描述：上面的所有方法进行限定IP
     */
    @Before("limitIp(){}")
    public void doBefore(final JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LimitIp limitIp = method.getAnnotation(LimitIp.class);

        HttpServletRequest request;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        request = attributes.getRequest();
        if (request == null) {
            throw new Exception("方法中缺失HttpServletRequest参数");
        }

        String methodName = joinPoint.getSignature().getName();

        String ip = IpUtils.getIpAddr(request);
        String[] limitIpString = limitIp.ip().split(",");
        if (!ArrayUtil.contains(limitIpString, ip)) {
//            throw new SystemException("非法IP[".concat(ip).concat("]请求[").concat(methodName).concat("]"), 403);
        }

    }
}