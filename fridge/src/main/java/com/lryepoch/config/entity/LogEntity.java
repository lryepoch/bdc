package com.lryepoch.config.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/10/9 16:13
 * @description TODO 日志实体
 */
@Entity
@Data
@Table(name = "t_log")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
    * 邮箱
    */
    private Integer account;
    /**
    * 姓名
    */
    private String name;
    /**
    * 访问页面
    */
    private String pageName;
    /**
    * 访问请求
    */
    private String requestUrl;
    /**
    * 访问时间
    */
    private Date requestTime;

}
