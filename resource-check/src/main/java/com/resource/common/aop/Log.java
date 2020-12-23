package com.resource.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @description 接口日志类
* @author lryepoch
* @date 2020/12/12 14:38
*
*/
@Target({ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    //业务操作描述
    String description() default "";
}
