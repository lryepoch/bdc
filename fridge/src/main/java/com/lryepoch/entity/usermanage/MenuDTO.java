package com.lryepoch.entity.usermanage;

import lombok.Data;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/30 16:02
 * @description TODO 菜单实体
 */
@Data
public class MenuDTO {
    /**
     * 菜单ID
     */
    private Integer id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 父id
     */
    private Integer parentId;
    /**
     * 子节点
     */
    List<MenuVO> childrens;
}
