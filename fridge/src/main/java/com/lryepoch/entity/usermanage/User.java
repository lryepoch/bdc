package com.lryepoch.entity.usermanage;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/29 11:21
 * @description TODO 用户实体
 */
@Data
@Table(name = "t_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer account;

    private String userName;

    private String department;

    private String company;

    private Date updateTime;

    private Integer deleted;

}
