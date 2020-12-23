package com.gtsc.config.interceptor;

import com.gtsc.annotation.CheckToken;
import com.gtsc.annotation.IgnoreToken;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author resource
 * @date 2020/9/8 9:02
 * @description TODO 拦截器组件
 */
//@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从http请求头中取出token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有pass token注释，有则跳过认证
        if (method.isAnnotationPresent(IgnoreToken.class)) {
            IgnoreToken ignoreToken = method.getAnnotation(IgnoreToken.class);
            if (ignoreToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
//                    throw GreeException.throwException("请重新登录", HttpStatus.UNAUTHORIZED.getValue());
                }
//                // 获取 token 中的 userName
//                String userName = null;
//                try {
//                    userName = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
////                    throw GreeException.throwException("无效token,请重新登录",HttpStatus.UNAUTHORIZED.getValue());
//                }
//                UserDto user = userService.findByName(userName);
//                if (user == null) {
////                    throw  GreeException.throwException("无效token,请重新登录",HttpStatus.UNAUTHORIZED.getValue());
//                }
//                // 验证 token
//                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getAppKey())).build();
//                try {
//                    jwtVerifier.verify(token);
//                } catch (JWTVerificationException e) {
////                    throw GreeException.throwException("token失效,请重新登录",HttpStatus.UNAUTHORIZED.getValue());
//                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
