package com.resource.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author resource
 * @date 2020/9/8 8:46
 * @description TODO md5加密
 */
public class EncryptHelper {

    private static String algorithmName = "md5";
    private final static int hashIterations = 2;
    private final static String salt = "resource";
    public static String encryptData(String data) {
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        String newData = new SimpleHash(algorithmName, data, credentialsSalt, hashIterations).toHex();
        return newData;
    }

    public static void main(String[] args) {
        String data = "dev2!123";
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        String newData = new SimpleHash(algorithmName, data, credentialsSalt, hashIterations).toHex();
        System.out.println(newData);
    }

}
