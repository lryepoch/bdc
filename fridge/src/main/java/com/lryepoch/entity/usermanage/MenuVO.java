package com.lryepoch.entity.usermanage;

import lombok.Data;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/30 15:31
 * @description TODO
 */
@Data
public class MenuVO {

    private Integer id;

    private String name;

    List<MenuVO> childrens;
}
