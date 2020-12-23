package com.lryepoch.entity.usermanage;

import lombok.Data;

import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/9/29 15:59
 * @description TODO
 */
@Data
public class UserDTO {
    private Integer id;

    private Integer account;

    private String userName;

    private String department;

    private String company;

    private Date updateTime;

    private Integer deleted;

    private Integer roleId;
}
