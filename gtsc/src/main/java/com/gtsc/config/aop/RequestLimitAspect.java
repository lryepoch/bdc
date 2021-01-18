package com.gtsc.config.aop;

import com.gtsc.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * TODO 记录api请求次数信息到redis（需要配置redis服务器）
 *
 */
@Aspect
@Component
public class RequestLimitAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws Exception {
        HttpServletRequest request;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            request = attributes.getRequest();
            if (request == null) {
                throw new Exception("方法中缺失HttpServletRequest参数");
            }

            String ip = IpUtils.getIpAddr(request);
            String url = request.getRequestURL().toString();

            String key = "req_limit_".concat(url).concat(ip);
            long count = redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
            }
            if (count > limit.count()) {
                logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
//                throw new SystemException("用户IP[" + ip + "]频繁访问地址[" + url + "]", 403);
            }
        } catch (Exception e) {
            logger.error("发生异常: ", e);
            throw e;
        }
    }
}