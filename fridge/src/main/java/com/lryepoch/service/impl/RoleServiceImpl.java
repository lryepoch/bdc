package com.lryepoch.service.impl;

import com.lryepoch.dao.RoleQueryMapper;
import com.lryepoch.dao.jpa.RoleJpaMapper;
import com.lryepoch.entity.usermanage.MenuDTO;
import com.lryepoch.entity.usermanage.MenuVO;
import com.lryepoch.entity.usermanage.Role;
import com.lryepoch.service.RoleService;
import com.lryepoch.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/9/30 14:21
 * @description TODO
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleJpaMapper roleJpaMapper;
    @Autowired
    private RoleQueryMapper roleQueryMapper;

    /**
     * @return
     * @description 获取角色拥有的页面
     * @author lryepoch
     * @date 2020/9/30 15:47
     */
    @Override
    public List<MenuVO> getMenuTreeByRole(Role role) {
        //获取菜单页面集合
        List<MenuDTO> menus = roleQueryMapper.getMenuByRole(role.getId());
        //处理菜单页面集合并返回页面树
        return getMenuTree(menus);
    }

    /**
     * @description 根据菜单页面集合构成菜单页面树
     * @author lryepoch
     * @date 2020/9/30 17:01
     */
    private List<MenuVO> getMenuTree(List<MenuDTO> menus) {
        //1.获取父id是0的所有根节点页面
        List<MenuVO> parentNodes = menus.parallelStream().filter(m -> m.getParentId() == 0)
                .sorted(Comparator.comparingInt(MenuDTO::getId))
                .map(dto -> (MenuVO) ConverterUtils.toVO(dto, MenuVO.class))
                .collect(Collectors.toList());

        //2.将子节点的菜单添加到父节点中，这里默认只有两级，如果是多级可以增加一个已添加标记并迭代list
        parentNodes.parallelStream().forEach(p ->
                menus.forEach(m -> {
                    if (m.getParentId().equals(p.getId())) {
                        List<MenuVO> tempList = p.getChildrens() == null ? new LinkedList<>() : p.getChildrens();
                        tempList.add(ConverterUtils.toVO(m, MenuVO.class));
                        p.setChildrens(tempList);
                    }
                }));
        return parentNodes;
    }

    @Override
    public void addRole(Role newRole, List<Integer> menu) {
        newRole.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        newRole.setDeleted(1);
        Role role = roleJpaMapper.save(newRole);

        //删除可能存在的角色菜单对应关系
        roleQueryMapper.deleteRoleMenu(role.getId());

        //查询页面下附带的操作权限并插入角色权限关系表中
        if (menu.size()>0){
            List<MenuDTO> menuDTOList = roleQueryMapper.getAllMenuByMid(menu);
            if (menuDTOList.size()>0) {
                roleQueryMapper.insertRoleMenu(role.getId(), menuDTOList);
            }
        }
    }

    @Override
    public void deleteRole(Role role) {
        //逻辑删除角色
        role.setDeleted(0);
        role.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        roleJpaMapper.save(role);

        //删除角色权限关系
        roleQueryMapper.deleteRoleMenu(role.getId());
    }
}
