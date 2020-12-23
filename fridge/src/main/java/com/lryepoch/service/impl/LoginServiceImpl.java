package com.lryepoch.service.impl;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.PathEntity;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.config.entity.SsoEnum;
import com.lryepoch.config.interceptor.AllowCrossInterceptor;
import com.lryepoch.dao.UserQueryMapper;
import com.lryepoch.entity.usermanage.User;
import com.lryepoch.entity.usermanage.UserDTO;
import com.lryepoch.service.LoginService;
import com.lryepoch.service.SsoService;
import com.lryepoch.service.UserService;
import com.lryepoch.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/10/9 9:22
 * @description TODO
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SsoService ssoService;
    @Autowired
    private PathEntity pathEntity;
    @Autowired
    private UserService userService;

    @Override
    public CommonResult oneClickLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullTokenUrl = String.format(SsoEnum.URL.getString(), callback, SsoEnum.APP_ID.getString(), SsoEnum.APP_KEY.getString());
        Map token = ssoService.getSsoToken(fullTokenUrl);
        if (token == null) {
            //用户没有权限，那么跳到登录界面
            response.sendRedirect(pathEntity.getLoginUrl());
        }
        if (token != null && "true".equals(token.get("Success").toString())) {
            //获取登录人员的IP地址
            String ip = ssoService.getIpAddr(request);
            String fullUserInfoUrl = String.format(SsoEnum.USER_URL.getString(), token.get("Message").toString(),
                    SsoEnum.APP_ID.getString(), SsoEnum.APP_KEY.getString(), ip);
            //主要从sso中得到登录用户的信息
            Map ssoUserInfo = ssoService.getSsoUserInfo(fullUserInfoUrl);
            if (ssoUserInfo != null && "true".equals(ssoUserInfo.get("Success").toString())) {
                Integer account = Integer.valueOf(ssoUserInfo.get("AppAccount").toString());
                User user = userService.getUserByAccount(account);
                if (user == null) {
                    return CommonResult.fail(ResultEnum.ERR.getCode(), "用户无权限");
                } else {
                    request.getSession().setAttribute("ssoUserInfo", ssoUserInfo);

                    //将用户权限更新进权限校验拦截器
                    AllowCrossInterceptor.setMenuByUser(account, userService.getAllMenuByUser(account));

                    //用户名、部门名、岗位、任何一个不相等，则需要更新数据库用户信息
                    if (user.getUserName() == null || user.getUserName() != null && !user.getUserName().equals(ssoUserInfo.get("UserName"))) {
                        user.setUserName(String.valueOf(ssoUserInfo.get("UserName")));
                    }
                    if (user.getDepartment() == null || user.getDepartment() != null && !user.getDepartment().equals(ssoUserInfo.get("DepartmentName"))) {
                        user.setDepartment(String.valueOf(ssoUserInfo.get("DepartmentName")));
                    }
                    if (user.getCompany() == null || user.getCompany() != null && !user.getCompany().equals(ssoUserInfo.get("OrgL1Name"))) {
                        user.setCompany(String.valueOf(ssoUserInfo.get("OrgL1Name")));
                    }
                    userService.updateUser(ConverterUtils.toVO(user, UserDTO.class));

                    //成功跳转到系统首页
                    response.sendRedirect(pathEntity.getIndexUrl());
                    return CommonResult.success(ResultEnum.SUCCESS.getCode(),"登录成功");
                }
            }
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "单点登录或系统出现问题，请联系管理员");
    }

    @Override
    public CommonResult oneClickLogout(HttpServletRequest request) {
        String ip = ssoService.getIpAddr(request);
        HttpSession session = request.getSession();
        Map ssoUserInfo = (Map) session.getAttribute("ssoUserInfo");
        if (ssoUserInfo!=null){
            String outUrl = String.format(SsoEnum.LOGOUT_URL.getString(), ssoUserInfo.get("Token"), SsoEnum.APP_ID.getString(), SsoEnum.APP_KEY.getString(), ip);
            if (outUrl!=null){
                ssoService.getSsoOut(outUrl);
                session.removeAttribute("ssoUserInfo");
            }
            return CommonResult.success(ResultEnum.SUCCESS.getCode(),"登出成功");
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "登出失败");
    }
}
