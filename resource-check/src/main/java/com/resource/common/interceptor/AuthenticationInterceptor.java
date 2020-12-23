package com.resource.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.resource.common.aop.Administrator;
import netscape.security.Privilege;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * @author resource
 * @date 2020/9/8 9:02
 * @description TODO 拦截器组件
 */
//@Component       //此处无需添加@Component，因为InterceptorConfig中已经添加了@Bean
public class AuthenticationInterceptor implements HandlerInterceptor {
    Logger logger = LogManager.getLogger("resource");

    private static AuthenticationInterceptor authenticationInterceptor;

//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private PrivilegeService privilegeService;
//    @Autowired
//    private PrivilegeMapper privilegeMapper;
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private RolePrivilegeMapper rolePrivilegeMapper;
//
//    @PostConstruct
//    public void init() {
//        authenticationInterceptor = this;
//        authenticationInterceptor.userMapper = this.userMapper;
//        authenticationInterceptor.privilegeService = this.privilegeService;
//        authenticationInterceptor.privilegeMapper = this.privilegeMapper;
//        authenticationInterceptor.roleMapper = this.roleMapper;
//        authenticationInterceptor.roleService = this.roleService;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long start = System.currentTimeMillis();
//        if (1==1)return true;
        //从http请求头中读取token
        String token = request.getHeader("token");
        System.out.println("token==" + token);
        //除了登录接口其他接口都要验证是否登录
        String url = request.getRequestURI();
        insertPrivilege(url, handler);
        //判断用户是否登录且是否具有权限（）
        Boolean isOrNotLogin = isOrNotLogin(request, response, handler);
        System.out.println("是否登录：" + isOrNotLogin);
        Long end = System.currentTimeMillis();
        System.out.println(url + "登录所花费的时间" + (end - start));
        return isOrNotLogin;
    }

    /**
     * 判断用户是否登录
     *
     * @param request
     * @param response
     * @return
     */
    private Boolean isOrNotLogin(HttpServletRequest request, HttpServletResponse response, Object object) {
        //从http请求头中读取token
//        String token = request.getHeader("token");
//        //除了登录接口其他接口都要验证是否登录
//        String url = request.getRequestURI();
//        System.out.println("url==**==" + url);
//        if (!url.equals("/user-info/onclickLogin") &&
//                !url.contains("/download-config/download") &&
//                !url.contains("/validateAccount") &&
//                !url.contains("/generateToken") &&
//                !url.contains("/user-info/OneClickLogin") &&
//                !url.contains("swagger")) {
//            //执行认证
//            if (token == null) {
//                throw new SystemException(ResultEnum.ERR.getCode(), "无token，请重新登录");
//            }
//            //登录操作，判断登录密码是否正确
//            //获取token中的email
//            String email = JWT.decode(token).getAudience().get(0);
//            User user = authenticationInterceptor.userMapper.selectByEmail(email);
//            if (user == null) {
//                throw new SystemException(ResultEnum.ERR.getCode(), "用户不存在，请重新登录资源签审系统");
//            }
//            //验证token
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//            try {
//                jwtVerifier.verify(token);
//            } catch (JWTVerificationException E) {
//                throw new SystemException(ResultEnum.ERR.getCode(), "若修改过开机密码，请联系管理员重置密码");
//            }
//            //判断用户是否有该接口的权限
//            Boolean hasPrivilege = hasPrivilege(url, email, object);
//            logger.info("是否有权限：" + hasPrivilege);
//            if (!hasPrivilege) {
//                throw new SystemException(ResultEnum.ERR.getCode(), "当前操作暂无权限");
//            }
//        }
        return true;
    }

    /**
     * 判断用户是否有该权限
     *
     * @param url
     * @param email
     * @return
     */
    private Boolean hasPrivilege(String url, String email, Object object) {
        if ("/user-info/onclickLogin".equals(url)) {
            return true;
        }
        if ("000000".equals(email)) {
            return true;
        }
        if (object.toString().contains("com.resource")) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();

            //检查用户有没有此权限
            if (method.isAnnotationPresent(Administrator.class)) {
//                User user = authenticationInterceptor.userMapper.selectByEmail(email);
//                if (user == null) return false;
//                List<Roles> rolesList = user.getRolesList();
//                StringBuilder rolesb = new StringBuilder();
//                StringBuilder urlsb = new StringBuilder();
//                for (Roles roles : rolesList) {
//                    rolesb.append(roles.getRoleName()).append(",");
//                    List<Privileges> privilegesList = roles.getPrivilegesList();
//                    for (Privileges privileges : privilegesList) {
//                        urlsb.append(privileges.getUrl()).append(";");
//                    }
//                }
//                if (rolesb.toString().contains("admin"))
//                    return true;
//                if (urlsb.toString().contains(url)) {
//                    return true;
//                } else {
                return false;
//                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 添加权限（自动）
     */
    @Async
    void insertPrivilege(String url, Object object) {
//        if (object.toString().contains("com.resource")) {
//            HandlerMethod handlerMethod = (HandlerMethod) object;
//            Method method = handlerMethod.getMethod();
//
//            //检查用户有没有此权限
//            if (method.isAnnotationPresent(Administrator.class)) {
//                Administrator administrator = method.getAnnotation(Administrator.class);
//                String label = administrator.label();
//                String description = administrator.description();
//                Privilege privilege = new Privilege();
//                privilege.setDescription(description);
//                privilege.setUrl(url);
//                authenticationInterceptor.privilegeService.insert(privilege);
//
//                //添加角色权限
//                Privilege insertPrivilege = authenticationInterceptor.privilegeMapper.selectByUrl(url);
//                String[] roleNameArray = label.replace("，", ",").split(",");
//                for (int i = 0; i < roleNameArray.length; i++) {
//                    String roleName = roleNameArray[i];
//                    Role role = authenticationInterceptor.roleMapper.selectByRoleName(roleName);
//                    RolePrivilege rolePrivilege = new RolePrivilege();
//                    rolePrivilege.setRoleId(role.getId());
//                    rolePrivilege.setPrivilegeId(insertPrivilege.getId());
//                    authenticationInterceptor.roleService.roleAddPrivilege(rolePrivilege);
//                    //删除没有权限的角色权限
//                    List<Role> roleList = authenticationInterceptor.roleMapper.selectByPrivilegeId(insertPrivilege.getId());
//                    for (Role r : roleList) {
//                        if (!label.contains(r.getRoleName())) {
//                            //删除关联关系
//                            authenticationInterceptor.rolePrivilegeMapper.deleteByPrivilegeIdRoleId(insertPrivilege.getId(), r.getId());
//                        }
//                    }
//                }
//            }
//        }

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
