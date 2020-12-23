package com.lryepoch.config.entity;

import lombok.Getter;

/**
 * @author lryepoch
 * @date 2020/10/9 9:40
 * @description TODO
 */
@Getter
public enum  SsoEnum {
    /**
     * 系统调用
     */
    RANDOM_STRING("abcdefghijklmnopqrstuvwxyz"),

    APP_ID("df75abc-30cd-64dd-92f8-91678a167eec4"),
    APP_KEY("a297d664-921a-4378-bef6-5eab00c1634"),
    URL("http://wfserver.gree.com/sso/ssoapi/GetToken?callback=%s&appid=%s&appkey=%s"),
    USER_URL("http://wfserver.gree.com/sso/ssoapi/GetUserInfo?token=%s&appid=%s&appkey=%s&ip=%s"),
    LOGOUT_URL("http://wfserver.gree.com/sso/ssoapi/SignOut?token=%s&appid=%s&appkey=%s&ip=%s");

    String string;

    SsoEnum(String string) {
        this.string = string;
    }
}
