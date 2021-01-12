package com.lryepoch.config.log;

import com.lryepoch.config.entity.LogEntity;
import com.lryepoch.dao.RoleQueryMapper;
import com.lryepoch.dao.jpa.PageLogJpaMapper;
import com.lryepoch.entity.usermanage.Menu;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lryepoch
 * @date 2020/10/9 9:00
 * @description TODO 页面访问日志注解切面
 */
@Aspect
@Component
public class PageLogAspect {
    @Autowired
    private PageLogJpaMapper logJpaMapper;
    @Resource
    private RoleQueryMapper roleQueryMapper;

    private static List<Menu> menuList = new ArrayList<>();

    @Pointcut("@annotation(com.lryepoch.config.log.PageLog)")
    public void controllerAspect(){}

    @After("controllerAspect()")
    public void writeLog(){
        HttpServletRequest request = ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Map ssoUserInfo = (Map) request.getSession().getAttribute("ssoUserInfo");
        if (menuList.size()==0){
            menuList = roleQueryMapper.getAllOperaAndMenu();
        }
        String url = request.getRequestURI().substring(7);
        if ("/getToken".equals(url)){

        }
        String pageName = menuList.parallelStream().filter(m->url.equals(m.getRequestUrl()))
                .map(m1->{
                    if (m1.getType()==2){
                        String str = menuList.stream().filter(g->g.getId()==m1.getParentId())
                                .findFirst().orElseGet(Menu::new).getDescription();
                        return str;
                    } else {
                        return m1.getDescription();
                    }
                }).findFirst().orElse(null);
        LogEntity log = new LogEntity();
        log.setAccount(Integer.valueOf(ssoUserInfo.get("AppAccount").toString()));
        log.setName(String.valueOf(ssoUserInfo.get("UserName")));
        log.setPageName(pageName);
        log.setRequestUrl(url);
        log.setRequestTime(Timestamp.valueOf(LocalDateTime.now()));
        logJpaMapper.save(log);
    }
}
