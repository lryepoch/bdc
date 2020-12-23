package com.resource.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lryepoch
 * @date 2020/12/14 15:12
 * @description TODO
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Administrator {
    //业务操作描述
    String description() default "";
    String label() default "";
}
