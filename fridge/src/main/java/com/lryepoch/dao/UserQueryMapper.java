package com.lryepoch.dao;

import com.lryepoch.entity.usermanage.RoleVO;
import com.lryepoch.entity.usermanage.User;
import com.lryepoch.entity.usermanage.UserRoleVO;

import java.util.Optional;

/**
 * @author lryepoch
 * @date 2020/9/29 18:01
 * @description TODO
 */
public interface UserQueryMapper {
    void deleteUserRole(Integer uId);

    void addUserRole(UserRoleVO userRoleVO);

    User searchUser(String str);

    Optional<RoleVO> getRoleByUser(Integer account);
}
