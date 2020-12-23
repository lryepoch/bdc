package com.lryepoch.entity.usermanage;

import lombok.Data;

/**
 * @author lryepoch
 * @date 2020/9/29 11:21
 * @description TODO 菜单权限实体
 */
@Data
public class Menu {
    private Integer id;

    private String description;

    private String requestUrl;

    private String pictureUrl;
    /*父id*/
    private Integer parentId;
    /*菜单类型，1：页面；2：操作*/
    private Integer type;

    private Integer deleted;

}
