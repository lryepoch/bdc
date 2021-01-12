package com.lryepoch.config.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author lryepoch
 * @date 2020/9/29 13:49
 * @description TODO 自定义数据源
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSourceEntity {

    /**
    * 数据库连接
    */
    private String url;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String password;

    /**
    * 数据库驱动
    */
    private String driverName;

    /**
    * xml映射文件位置
    */
    private String mapperPath;
}
