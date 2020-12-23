package com.gtsc.config.aop;

import java.lang.annotation.*;

/**
 * @author
 * @date
 * @描述 定时任务限定ip注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LimitIp {
    //配置多个IP以英文逗号隔开
    String ip() default "127.0.0.1";
}