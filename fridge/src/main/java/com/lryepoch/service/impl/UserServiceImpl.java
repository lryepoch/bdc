package com.lryepoch.service.impl;

import com.lryepoch.config.interceptor.AllowCrossInterceptor;
import com.lryepoch.dao.RoleQueryMapper;
import com.lryepoch.dao.UserQueryMapper;
import com.lryepoch.dao.jpa.UserJpaMapper;
import com.lryepoch.entity.usermanage.*;
import com.lryepoch.service.UserService;
import com.lryepoch.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/9/29 16:18
 * @description TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserJpaMapper userJpaMapper;
    @Autowired
    private UserQueryMapper userQueryMapper;
    @Autowired
    private RoleQueryMapper roleQueryMapper;

    /**
     * @description 添加用户
     * @author lryepoch
     * @date 2020/9/29 16:49
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addUser(UserDTO userDTO) {
        //jpa新增和修改的api一样
        userService.updateUser(userDTO);
    }

    /**
     * @description 修改用户
     * @author lryepoch
     * @date 2020/9/29 16:55
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateUser(UserDTO userDTO) {
        //将用户的权限更新进权限校验拦截器
        AllowCrossInterceptor.setMenuByUser(userDTO.getAccount(), getAllMenuByUser(userDTO.getAccount()));

        userDTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        //保存用户信息到用户表
        User user = userJpaMapper.save(ConverterUtils.toVO(userDTO, User.class));
        //保存用户角色关系到用户角色关系表
        if (userDTO.getRoleId() != null) {
            UserRoleVO userRoleVO = new UserRoleVO();
            userRoleVO.setUId(user.getId());
            userRoleVO.setRId(userDTO.getRoleId());
            userService.updateRoleOfUser(userRoleVO);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateRoleOfUser(UserRoleVO userRoleVO) {
        //先清除用户角色关系
        userQueryMapper.deleteUserRole(userRoleVO.getUId());
        //再插入对应的用户角色关系
        userQueryMapper.addUserRole(userRoleVO);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteUser(User user) {
        //逻辑删除，设置删除标志和更新时间
        user.setDeleted(0);
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userJpaMapper.save(user);
        //删除用户角色关系
        userQueryMapper.deleteUserRole(user.getId());
    }

    @Override
    public UserVO searchUser(String str) {
        User user = userQueryMapper.searchUser(str);
        return ConverterUtils.toVO(user, UserVO.class);
    }

    @Override
    public List<UserDTO> getAllUser() {
        //所有用户
        List<User> users = userJpaMapper.findAllByDeleted(1);
        //查找所有用户对应的角色
        List<UserDTO> userDTOS = users.parallelStream().map(user -> {
            UserDTO dto = ConverterUtils.toVO(user, UserDTO.class);
            dto.setRoleId(userQueryMapper.getRoleByUser(user.getAccount()).isPresent() ? userQueryMapper.getRoleByUser(user.getAccount()).get().getId() : null);
            return dto;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public User getUserByAccount(Integer account) {
        Optional<User> userOptional = Optional.ofNullable(userJpaMapper.findUserByAccountAndDeleted(account, 1));
        return userOptional.orElse(null);
    }

    /**
    * @description 根据邮箱号查找拥有的全部页面及操作权限url
    * @author lryepoch
    * @date 2020/10/9 15:22
    *
    */
    @Override
    public List<String> getAllMenuByUser(Integer account) {
        Optional<RoleVO> existRole = userQueryMapper.getRoleByUser(account);
        List<Menu> list = new ArrayList<>();
        if (existRole.isPresent()){
            list = roleQueryMapper.getAllMenuByRole(existRole.get().getId());
        }
        return list.parallelStream().map(Menu::getRequestUrl).collect(Collectors.toList());
    }

}
