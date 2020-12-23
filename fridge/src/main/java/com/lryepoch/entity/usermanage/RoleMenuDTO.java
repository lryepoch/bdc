package com.lryepoch.entity.usermanage;

import lombok.Data;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/29 11:22
 * @description TODO
 */
@Data
public class RoleMenuDTO {
    private int id;

    private String roleName;

    private List<Integer> menu;
}
