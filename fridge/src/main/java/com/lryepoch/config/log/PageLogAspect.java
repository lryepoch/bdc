package com.lryepoch.config.log;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/10/9 9:00
 * @description TODO 页面访问日志注解切面
 */
@Aspect
@Component
public class PageLogAspect {

    @Pointcut("@annotation(com.lryepoch.config.log.PageLog)")
    public void controllerAspect(){}

    @After("controllerAspect()")
    public void writeLog(){

    }
}
