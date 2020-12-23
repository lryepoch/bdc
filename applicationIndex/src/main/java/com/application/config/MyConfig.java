package com.application.config;

import com.application.filter.AdminSecurityInterceptor;
import com.application.filter.UserSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lryepoch
 * @date 2020/12/14 14:05
 * @description TODO
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

    @Bean
    public AdminSecurityInterceptor getAdminInteger(){
        return new AdminSecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor())
                .excludePathPatterns("/sso/login")
                .excludePathPatterns("/sso/logout")
                .excludePathPatterns("swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/swagger-ui.html/**");

        registry.addInterceptor(getAdminInteger())
                .addPathPatterns("/admin/**")
                .addPathPatterns("/user/**");
    }
}
