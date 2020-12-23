package com.lryepoch.service;

import com.lryepoch.entity.usermanage.MenuVO;
import com.lryepoch.entity.usermanage.Role;
import com.lryepoch.entity.usermanage.RoleMenuDTO;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/30 14:21
 * @description TODO
 */
public interface RoleService {

    List<MenuVO> getMenuTreeByRole(Role role);

    void addRole(Role newRole, List<Integer> menu);

    void deleteRole(Role role);

}
