package com.lryepoch.dao;

import com.lryepoch.entity.usermanage.Menu;
import com.lryepoch.entity.usermanage.MenuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/30 14:23
 * @description TODO
 */
public interface RoleQueryMapper {
    List<MenuDTO> getMenuByRole(Integer rid);

    void deleteRoleMenu(Integer id);

    /**
    * @description 根据菜单id获取完整的菜单及下挂的操作权限
    * @author lryepoch
    * @date 2020/10/8 11:23
    *
    */
    List<MenuDTO> getAllMenuByMid(List<Integer> menus);

    void insertRoleMenu(@Param("rid") Integer id, @Param("list") List<MenuDTO> menuDTOList);

    List<Menu> getAllMenuByRole(int id);

    /**
    * 获取所有页面和操作
    */
    List<Menu> getAllOperaAndMenu();
}
