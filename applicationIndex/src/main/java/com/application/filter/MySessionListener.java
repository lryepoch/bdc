package com.application.filter;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/12/14 14:15
 * @description TODO session的创建和失效
 */
@WebListener
public class MySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Map ssoUserInfo = (Map) session.getAttribute("ssoUserInfo");

        if (ssoUserInfo!=null){
//            String outUrl = String.format(LoginUtils.logoutUrl, ssoUserInfo.get("Token"), LoginUtils.appId, LoginUtils.appKey, session.getAttribute("ip"));
//            LoginController.sendGet(outUrl);
        }
    }
}
