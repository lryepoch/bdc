package com.lryepoch.dao.jpa;

import com.lryepoch.entity.usermanage.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/30 14:22
 * @description TODO
 */
public interface RoleJpaMapper extends JpaRepository<Role, Integer> {
    Role findByIdAndDeleted(Integer rid, int i);

    /**
    * @description 查找所有启用的角色
    * @author lryepoch
    * @date 2020/10/8 9:49
    *
    */
    List<Role> findAllByDeleted(int i);

    Role findByRoleNameAndDeleted(String roleName, int i);
}
