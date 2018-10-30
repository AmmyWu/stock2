/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.common.util
 * 类/接口名: MD5Utils
 * 版本信息	: 1.00
 * 新建日期	: 2018年8月21日上午9:01:34
 * 作者		: taofeng
 * 修改历史	: 2018年8月21日上午9:01:34
 */
package com.stock.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName	: MD5Utils
 * Function		: TODO
 * date 		: 2018年8月21日上午9:01:34
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
public class MD5Utils {
    public String getMD5(String password){
        String md5Password = null;
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            md5Password = buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Password;
    }
}
