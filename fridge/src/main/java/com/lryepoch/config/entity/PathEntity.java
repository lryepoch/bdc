package com.lryepoch.config.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/10/9 10:29
 * @description TODO 各种请求路径
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "path")
public class PathEntity {

    /**
    * 图片路径
    */
    private String picturePath;

    /**
    * excel路径
    */
    private String excelPath;

    /**
    * nginx或本地的后台请求路径，为了给图片拼地址使用
    */
    private String localPath;

    /**
    * 登录地址
    */
    private String loginUrl;

    /**
    * 登陆后转向的首页地址
    */
    private String indexUrl;
}
