package com.lryepoch.entity.usermanage;

import lombok.Data;


/**
 * @author lryepoch
 * @date 2020/9/30 9:54
 * @description TODO
 */
@Data
public class UserVO {
    private Integer account;

    private String userName;

    private String department;

    private String company;

    private Integer deleted;

}
