package com.application.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author lryepoch
 * @date 2020/12/14 14:03
 * @description TODO 检查接口权限（超管和管理员）
 */
public class AdminSecurityInterceptor implements HandlerInterceptor {

//    @Resource
//    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String str="OPTIONS";
//        if (request.getMethod().equals(str)) {
//            return true;
//        }
//
//        User user = userService.getUserByAppAccount(userService.getAppAccount(request));
//
//        /*判断是否有/admin，确定是否是管理员*/
//        boolean isSuperAdmin = request.getRequestURI().contains("/admin/");
//
//        if (isSuperAdmin) {
//            if (user.getGroups().getGroupId() == 1 && user.getGroups().getGroupName().equals("超级管理员")) {
//                return true;
//            }
//
//        } else {
//            boolean existed = (user.getGroups().getGroupId() == 1 || user.getGroups().getGroupId() == 2)
//                    && user.getGroups().getGroupName().contains("管理员");
//
//            if (existed) {
//                return true;
//            }
//        }
//
//        //否则，是普通用户
//        //request.setCharacterEncoding()设置从request中取得的值或者从数据库中取出的值
//        //设置HTTP响应的编码，同时指定浏览器显示的编码
//        response.setContentType("application/json; chartset=UTF-8");
//        //设置HTTP响应的编码，如果前面使用了response.setContentType()设置了编码，则会覆盖之前的设置
//        response.setCharacterEncoding("UTF-8");
//
//        PrintWriter pw = response.getWriter();
//
//        //将对象序列化成字符串，然后将提示信息输出到页面
//        pw.write(JsonUtil.objectToString(new ResponseResult(false, HttpStatus.SESSION_EXPIRED, "权限不足!请联系管理员！")));
//        pw.flush();
//        pw.close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
