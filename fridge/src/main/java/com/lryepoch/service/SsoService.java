package com.lryepoch.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/10/9 9:49
 * @description TODO
 */
public interface SsoService {
    Map getSsoToken(String fullTokenUrl);

    String getIpAddr(HttpServletRequest request);

    Map getSsoUserInfo(String fullUserInfoUrl);

    Map getSsoOut(String outUrl);
}
