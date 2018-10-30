/**
 * hongshiwl.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.stock.common.tool.db;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密DB用户名、密码专用加密工具类
 */
public class AesDBUtil {
    private static final ThreadLocal<Cipher> encrptCipher       = new ThreadLocal<Cipher>();

    private static final ThreadLocal<Cipher> decryptCipher      = new ThreadLocal<Cipher>();

    private static final String              pwd                = "ZJHZHSWL_DB_HELLO1234";
    /** 
     * 密钥算法 
     * java6支持56位密钥，bouncycastle支持64位 
     * */
    private static final String              KEY_ALGORITHM      = "AES";
    /** 
     * 加密/解密算法/工作模式/填充方式 
     *  
     * JAVA6 支持PKCS5PADDING填充方式 
     * Bouncy castle支持PKCS7Padding填充方式 
     * */
    private static final String              CIPHER_ALGORITHM   = "AES/CBC/PKCS5Padding";
    private static byte[]                    iv                 = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9,
            0x17, 3, 1, 6, 8, 0xC, 0xD, 91                     };

    private static final int                 HASH_ITERATIONS    = 10000;

    private static final int                 KEY_LENGTH         = 128;

    private static byte[]                    salt               = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            0xA, 0xB, 0xC, 0xD, 0xE, 0xF                       };                          // must save this for next time we want the key
    private static PBEKeySpec                myKeyspec          = new PBEKeySpec(pwd.toCharArray(),
                                                                    salt, HASH_ITERATIONS,
                                                                    KEY_LENGTH);
    private static IvParameterSpec           ivParameterSpec    = new IvParameterSpec(iv);
    private static final String              KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 加密成base64的字符串
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static String encryptToBase64(byte[] content) throws Exception {
        byte[] resultBytes = encrypt(content);
        return Base64.encodeBase64URLSafeString(resultBytes);
    }

    /**
     * 通过base64的字符串解密
     * 
     * @param base64Content
     * @return
     * @throws Exception
     */
    public static byte[] decryptFromBase64(String base64Content) throws Exception {
        byte[] byteAfterDecode = Base64.decodeBase64(base64Content.getBytes());
        return decrypt(byteAfterDecode);
    }

    /** 
     * 加密 
     * 
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */
    public static byte[] encrypt(byte[] content) throws Exception {
        Cipher cipher = encrptCipher.get();
        if (cipher == null) {
            SecretKeySpec key = genKey();
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器  
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);// 初始化  
            encrptCipher.set(cipher);
        }
        byte[] result = cipher.doFinal(content);
        return result; // 加密  
    }

    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */
    public static byte[] decrypt(byte[] content) throws Exception {
        Cipher cipher = decryptCipher.get();
        if (cipher == null) {
            SecretKeySpec key = genKey();
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);// 初始化
            decryptCipher.set(cipher);
        }
        byte[] result = cipher.doFinal(content);
        return result; // 加密 
    }

    /**
     * 生成密钥
     * 
     * @param password
     * @return
     * @throws Exception
     */
    private static SecretKeySpec genKey() throws Exception {
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
        SecretKey sk = keyfactory.generateSecret(myKeyspec);
        SecretKeySpec key = new SecretKeySpec(sk.getEncoded(), KEY_ALGORITHM);
        return key;
    }

}
