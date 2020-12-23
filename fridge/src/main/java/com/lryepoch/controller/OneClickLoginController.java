package com.lryepoch.controller;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lryepoch
 * @date 2020/10/9 9:13
 * @description TODO
 */
@Api(description = "单点登录操作")
@Controller
@RequestMapping(value = "/oneClick")
public class OneClickLoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "单点登录")
    @ResponseBody
    @PostMapping(value = "/oneClickLogin")
    public CommonResult oneClickLogin(@RequestParam("callback") String callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return loginService.oneClickLogin(callback, request, response);
    }

    @ApiOperation(value = "单点登出")
    @ResponseBody
    @PostMapping(value = "/oneClickLogout")
    public CommonResult oneClickLogout(HttpServletRequest request) {
        return loginService.oneClickLogout(request);
    }
}
