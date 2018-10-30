package com.stock.common.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wwwwei on 17/11/15.
 */
public class SignUtils {

    public static String getRequestSign(Map<String, String> params) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = String.valueOf(params.get(key));
            if (!key.isEmpty() && !value.isEmpty() && !key.equals("sign"))
                query.append(key).append(value);
        }
        System.out.println(query);
        // 第三步: 获取MD5
        return encryptMD5(query.toString());
    }

    private static String encryptMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes();

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = byte2hex(buff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(md5str);
        return md5str;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
}
