package com.lryepoch.config.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/11/12 14:00
 * @description TODO ftp服务器配置信息
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpEntity {
    /**
    * ftp主机
    */
    private String host;
    /**
    * 端口
    */
    private int port;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 临时文件目录
    */
    private String tempPath;
    /**
    * 图片目录
    */
    private String picPath;
}
