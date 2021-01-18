package com.lryepoch.controller;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/10/9 9:13
 * @description TODO
 */
@Api(description = "单点登录登出控制器")
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


    @ApiOperation(value = "判断session是否过期")
    @GetMapping(value = "checkSession")
    public CommonResult checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map ssoUserInfo = (Map) session.getAttribute("ssoUserInfo");
        if (ssoUserInfo != null) {
            if (ssoUserInfo.containsKey("AppAccount")) {
                return CommonResult.success("session存在");
            }
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "session过期");
    }

}
