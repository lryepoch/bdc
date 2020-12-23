package com.lryepoch.entity.usermanage;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/29 11:21
 * @description TODO 角色实体
 */
@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    private Date updateTime;

    private Integer deleted;
}
