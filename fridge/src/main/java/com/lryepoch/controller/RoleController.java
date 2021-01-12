package com.lryepoch.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.config.log.PageLog;
import com.lryepoch.dao.jpa.RoleJpaMapper;
import com.lryepoch.entity.usermanage.MenuVO;
import com.lryepoch.entity.usermanage.Role;
import com.lryepoch.entity.usermanage.RoleMenuDTO;
import com.lryepoch.service.RoleService;
import com.lryepoch.util.ConverterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author lryepoch
 * @date 2020/9/29 15:37
 * @description TODO
 */
@Api(description = "角色控制器")
@RestController
@RequestMapping(value = "role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleJpaMapper roleJpaMapper;

    @ApiOperation(value = "获取所有角色的页面权限")
    @GetMapping(value = "/getAllRoles")
    @PageLog
    public CommonResult getAllRoles() {
        List<Role> roles = roleJpaMapper.findAllByDeleted(1);
        JSONArray array = new JSONArray();
        roles.parallelStream().forEach(r -> {
            JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(r));
            List<MenuVO> menuTreeByRole = roleService.getMenuTreeByRole(r);
            obj.put("menu", menuTreeByRole);
            array.add(obj);
        });
        return CommonResult.success(array);
    }

    @ApiOperation(value = "获取某个角色拥有的页面权限")
    @PostMapping(value = "/getRoleMenuTree")
    public CommonResult getRoleMenuTree(@RequestBody Integer rid) {
        Optional<Role> roleOptional = Optional.ofNullable(roleJpaMapper.findByIdAndDeleted(rid, 1));
        if (roleOptional.isPresent()) {
            return CommonResult.success(roleService.getMenuTreeByRole(roleOptional.get()));
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "角色不存在");
    }

    @ApiOperation(value = "添加角色")
    @PostMapping(value = "/addRole")
    public CommonResult addRole(@RequestBody RoleMenuDTO roleMenuDTO){
        Optional<Role> role = Optional.ofNullable(roleJpaMapper.findByRoleNameAndDeleted(roleMenuDTO.getRoleName(), 1));
        if(role.isPresent()){
            return CommonResult.fail(ResultEnum.ERR.getCode(), "角色名或者id对应角色已存在");
        }
        Role newRole = new Role();
        newRole.setRoleName(roleMenuDTO.getRoleName());

        roleService.addRole(newRole, roleMenuDTO.getMenu());
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "角色添加成功");
    }

    @ApiOperation(value = "修改角色")
    @PostMapping(value = "/modifyRole")
    public CommonResult modifyRole(@RequestBody RoleMenuDTO roleMenuDTO){
        Optional<Role> role = Optional.ofNullable(roleJpaMapper.findByIdAndDeleted(roleMenuDTO.getId(), 1));
        if(!role.isPresent()){
            return CommonResult.fail(ResultEnum.ERR.getCode(), "角色名或者id对应角色不存在");
        }
        Role newRole = ConverterUtils.toVO(roleMenuDTO, Role.class);
        roleService.addRole(newRole, roleMenuDTO.getMenu());
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "角色修改成功");
    }

    @ApiOperation(value = "删除角色")
    @PostMapping(value = "/deleteRole")
    public CommonResult deleteRole(@RequestBody Integer id){
        Optional<Role> roleExist = Optional.ofNullable(roleJpaMapper.findByIdAndDeleted(id, 1));
        if(!roleExist.isPresent()){
            return CommonResult.fail(ResultEnum.ERR.getCode(), "角色名或者id对应角色不存在");
        }
        roleService.deleteRole(roleExist.get());
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "角色删除成功");
    }

}
