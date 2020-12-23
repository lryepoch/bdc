package com.application.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/12/14 14:10
 * @description TODO 检查session
 */
public class UserSecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");

        //当用户信息不存在时，知session已过期
        if (ssoUserInfo == null || !(ssoUserInfo.containsKey("AppAccount"))){
            //预请求，试探性地
            if(request.getMethod().equals("OPTIONS")){
                return true;
            }

            //实现跨域:把内容返回给前端的时候
            response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Methods",request.getMethod());
            response.setHeader("Access-Control-Allow-Credentials","true");

            response.setContentType("application/json; chartset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            PrintWriter pw = response.getWriter();
//            pw.write(JsonUtil.objectToString(new ResponseResult(false,HttpStatus.SESSION_EXPIRED,"登录过期，请重新登录")));
            pw.flush();
            pw.close();

            return false;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
