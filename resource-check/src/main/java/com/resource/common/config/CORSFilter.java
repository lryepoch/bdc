package com.resource.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author lryepoch
 * @date 2020/12/14 10:41
 * @description TODO 采用过滤方式解决跨域
 *
 */
@Configuration
public class CORSFilter {

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //是否支持安全证书，true表示允许发送cookie
        corsConfiguration.setAllowCredentials(true);
        //允许跨域访问的域名，"*"表示接受任意域名的请求
        corsConfiguration.addAllowedOrigin("*");
        //请求头
        corsConfiguration.addAllowedHeader("*");
        //请求方法，"*"表示服务器支持所有跨域请求的方法
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
