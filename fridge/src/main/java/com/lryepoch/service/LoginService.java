package com.lryepoch.service;

import com.lryepoch.config.entity.CommonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lryepoch
 * @date 2020/10/9 9:21
 * @description TODO
 */
public interface LoginService {
    /**
    * @description 单点登录
    * @author lryepoch
    * @date 2020/10/9 9:29
    *
    */
    CommonResult oneClickLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
    * @description 单点登出
    * @author lryepoch
    * @date 2020/10/9 9:29
    *
    */
    CommonResult oneClickLogout(HttpServletRequest request);
}
