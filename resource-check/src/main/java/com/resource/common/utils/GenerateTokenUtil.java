package com.resource.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;

/**
 * @author lryepoch
 * @date 2020/12/14 16:08
 * @description TODO
 */
public class GenerateTokenUtil {

    public static String getToken(String email, String password) throws UnsupportedEncodingException {
        String token = JWT.create().withAudience(email)
                .sign(Algorithm.HMAC256(EncryptHelper.encryptData(password)));
        return token;
    }
}
