package com.lryepoch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.service.SsoService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/10/9 9:50
 * @description TODO
 */
@Service
public class SsoServiceImpl implements SsoService {

    /**
    * @description 获取登录用户的token
    * @author lryepoch
    * @date 2020/10/9 9:53
    *
    */
    @Override
    public Map getSsoToken(String url) {
        return httpGet(url);
    }

    /**
    * @description 获取登录用户的信息
    * @author lryepoch
    * @date 2020/10/9 10:54
    *
    */
    @Override
    public Map getSsoUserInfo(String fullUserInfoUrl) {
        return httpGet(fullUserInfoUrl);
    }

    @Override
    public Map getSsoOut(String outUrl) {
        return httpGet(outUrl);
    }

    /**
    * @description 对请求的链接执行 http get 请求，返回json字符串，转换成map
    * @author lryepoch
    * @date 2020/10/9 9:55
    *
    */
    private Map httpGet(String url) {
        String json = sendGet(url);
        if (!"".equals(json.trim())){
            return JSONObject.parseObject(json, Map.class);
        }
        return null;
    }

    /**
    * @description 发送get请求
    * @author lryepoch
    * @date 2020/10/9 9:56
    *
    */
    private String sendGet(String url) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;

        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    @Override
    public String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "±¾µØ";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
