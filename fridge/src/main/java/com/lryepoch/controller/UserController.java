package com.lryepoch.controller;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.dao.jpa.UserJpaMapper;
import com.lryepoch.entity.usermanage.User;
import com.lryepoch.entity.usermanage.UserDTO;
import com.lryepoch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public CommonResult addUser(@RequestBody UserDTO userDTO){
        Optional<User> userOptional = Optional.ofNullable(userJpaMapper.findUserByAccountAndDeleted(userDTO.getAccount(), 1));
        if (userOptional.isPresent()){
            return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户已存在");
        }
        userService.addUser(userDTO);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "用户已添加");
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "updateUser")
    public CommonResult updateUser(@RequestBody UserDTO userDTO){
        Optional<User> userOptional = userJpaMapper.findById(userDTO.getId());
        if (userOptional.isPresent()){
            userService.updateUser(userDTO);
            return CommonResult.success(ResultEnum.SUCCESS.getCode(),"用户已修改");
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户不存在");
    }

    @ApiOperation(value = "删除用户")
    @PostMapping(value = "deleteUser")
    public CommonResult deleteUser(@RequestBody Integer id){
        Optional<User> userExist = userJpaMapper.findById(id);
        if (!userExist.isPresent()){
            return CommonResult.fail(ResultEnum.ERR.getCode(), "该用户不存在");
        }
        userService.deleteUser(userExist.get());
        return CommonResult.success(ResultEnum.SUCCESS.getCode(),"用户已删除");
    }

    @ApiOperation(value = "根据用户名或者邮箱查询用户")
    @PostMapping(value = "searchUser")
    public CommonResult searchUser(@RequestBody String str){
        return CommonResult.success(userService.searchUser(str));
    }

    @ApiOperation(value = "获取所有用户列表")
    @GetMapping(value = "getAllUser")
//    @PageLog
    public CommonResult getAllUser(){
        return CommonResult.success(userService.getAllUser());
    }

//    public CommonResult getCurrentUser(){
//
//    }
}
