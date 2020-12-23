package com.gtsc.entity;

import lombok.Data;

/**
 * @author lryepoch
 * @date 2020/12/12 14:23
 * @description TODO 日志参数实体
 */
@Data
public class ApiOperatorLog {
    private String beanName;
    private String methodName;
    private String uri;
    private String remoteAddr;
    private String sessionId;
    private String user;
    private String method;
    private String params;
    private long requestTime;
    private String curUser;
    private Object result;
    private String requestAddr;
}
