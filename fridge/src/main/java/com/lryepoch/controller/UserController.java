package com.lryepoch.controller;

import com.lryepoch.config.ReminderCache;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.config.log.PageLog;
import com.lryepoch.dao.UserQueryMapper;
import com.lryepoch.dao.jpa.UserJpaMapper;
import com.lryepoch.entity.usermanage.*;
import com.lryepoch.service.RoleService;
import com.lryepoch.service.UserService;
import com.lryepoch.util.ConverterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * @author lryepoch
 * @date 2020/9/29 15:37
 * @description TODO
 */
@Api(description = "用户控制器")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserJpaMapper userJpaMapper;
    @Autowired
    private UserService userService;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Resource
    private RoleService roleService;

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public CommonResult addUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOptional = Optional.ofNullable(userJpaMapper.findUserByAccountAndDeleted(userDTO.getAccount(), 1));
        if (userOptional.isPresent()) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户已存在");
        }
        userService.addUser(userDTO);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "用户已添加");
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "updateUser")
    public CommonResult updateUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userJpaMapper.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            userService.updateUser(userDTO);
            return CommonResult.success(ResultEnum.SUCCESS.getCode(), "用户已修改");
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户不存在");
    }

    @ApiOperation(value = "删除用户")
    @PostMapping(value = "deleteUser")
    public CommonResult deleteUser(@RequestBody Integer id) {
        Optional<User> userExist = userJpaMapper.findById(id);
        if (!userExist.isPresent()) {
            return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户不存在");
        }
        userService.deleteUser(userExist.get());
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "用户已删除");
    }

    @ApiOperation(value = "根据用户名或者邮箱查询用户")
    @PostMapping(value = "searchUser")
    public CommonResult searchUser(@RequestBody String str) {
        return CommonResult.success(userService.searchUser(str));
    }

    @ApiOperation(value = "获取所有用户列表")
    @GetMapping(value = "getAllUser")
//    @PageLog
    public CommonResult getAllUser() {
        return CommonResult.success(userService.getAllUser());
    }

    @ApiOperation(value = "获取当前用户信息")
    @PostMapping(value = "/getCurrentUser")
    public CommonResult getCurrentUser(HttpServletRequest request) {
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        if (ssoUserInfo == null) {
            return CommonResult.fail(new UserVO());
        }
        User user = userService.getUserByAccount(Integer.valueOf(ssoUserInfo.get("AppAccount").toString()));

        //获取上新价格列表
        HashSet<Integer> reminderSet = ReminderCache.map.get("reminderSet");
        //若存在上新价格
        if (reminderSet != null && reminderSet.size() > 0) {
            ReminderCache.map.putIfAbsent(user.getUserName(), new HashSet<>(reminderSet));
        }
        return CommonResult.success(ConverterUtils.toVO(user, UserVO.class));
    }

    @ApiOperation(value = "获取用户的角色，当有角色的时候返回角色，没角色的时候返回null")
    @GetMapping(value = "/getUserRole")
    public CommonResult getUserRole(HttpServletRequest request) {
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        Optional<RoleVO> existRole = userQueryMapper.getRoleByUser(userService.getUserByAccount(Integer.valueOf(ssoUserInfo.get("AppAccount").toString())).getAccount());
        if (existRole.isPresent()) {
            return CommonResult.success(existRole.get());
        } else {
            return CommonResult.fail(null);
        }
    }

    @ApiOperation(value = "修改用户的角色")
    @PostMapping(value = "/modifyUserRole")
    public CommonResult modifyUserRole(HttpServletRequest request, @RequestBody UserRoleVO userRoleVO) {
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        Optional<RoleVO> existRole = userQueryMapper.getRoleByUser(userService.getUserByAccount(Integer.valueOf(ssoUserInfo.get("AppAccount").toString())).getAccount());
        if (existRole.isPresent()) {
            userService.updateRoleOfUser(userRoleVO);
            return CommonResult.success(ResultEnum.SUCCESS.getCode(), "用户角色添加/修改成功");
        } else {
            return CommonResult.fail(null);
        }
    }

    @ApiOperation(value = "获取当前用户的页面权限树")
    @GetMapping(value = "/getMenuTree")
    public CommonResult getMenuTree(HttpServletRequest request) {
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        Optional<RoleVO> existRole = userQueryMapper.getRoleByUser(userService.getUserByAccount(Integer.valueOf(ssoUserInfo.get("AppAccount").toString())).getAccount());
        if (existRole.isPresent()) {
            return CommonResult.success(roleService.getMenuTreeByRole(ConverterUtils.toVO(existRole.get(), Role.class)));
        } else {
            return CommonResult.fail(null);
        }
    }
}
