package com.lryepoch.service;

import com.lryepoch.entity.usermanage.User;
import com.lryepoch.entity.usermanage.UserDTO;
import com.lryepoch.entity.usermanage.UserRoleVO;
import com.lryepoch.entity.usermanage.UserVO;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/27 9:36
 * @description TODO
 */
public interface UserService {

    void addUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void updateRoleOfUser(UserRoleVO userRoleVO);

    void deleteUser(User user);

    UserVO searchUser(String str);

    List<UserDTO> getAllUser();

    User getUserByAccount(Integer account);

    List<String> getAllMenuByUser(Integer account);
}
