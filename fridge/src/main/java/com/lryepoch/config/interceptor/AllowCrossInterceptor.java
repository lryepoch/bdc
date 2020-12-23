package com.lryepoch.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lryepoch
 * @date 2020/10/9 14:10
 * @description TODO 拦截器组件
 */
@Component
public class AllowCrossInterceptor implements HandlerInterceptor {

    /**
     * 主要考虑一个账号同时登录可能会出现的同步问题，此处用作登录若出现性能问题，换成HashMap也可，但一个账号多次登录put的时候get可能会出现死锁问题
     *
     * 登录时加载全部权限
     *
     */
    private static ConcurrentHashMap<Integer, List<String>> accountMenu = new ConcurrentHashMap<>();

    public static void setMenuByUser(Integer account, List<String> list) {
        accountMenu.put(account, list);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (1==1){return true;}
        response.setContentType("application/json; chartset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        if (ssoUserInfo == null || (ssoUserInfo.containsKey("AppAccount"))) {

            //实现跨域，把内容返回给前端的时候
//            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
//            response.setHeader("Access-Control-Allow-Headers", "*");
//            response.setHeader("Access-Control-Allow-Methods", request.getMethod());
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setContentType("application/json; chartset=UTF-8");
//            response.setCharacterEncoding("UTF-8");

            PrintWriter pw = response.getWriter();
            pw.write(JSON.toJSONString(CommonResult.fail(ResultEnum.ERR.getCode(), "请先登录")));
            pw.flush();
            pw.close();
            return false;
        }
        Integer account = Integer.valueOf(ssoUserInfo.get("AppAccount").toString());
        //账号是否有权限且包含正在请求的权限
        return accountMenu.get(account) != null && accountMenu.get(account).contains(request.getRequestURI().substring(7));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
