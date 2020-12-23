package com.lryepoch.config.log;

import java.lang.annotation.*;

/**
* @description 页面访问日志写入注解
* @author lryepoch
* @date 2020/10/8 17:19
*
*/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageLog {
}
