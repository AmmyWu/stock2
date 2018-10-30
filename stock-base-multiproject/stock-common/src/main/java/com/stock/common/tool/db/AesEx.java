package com.stock.common.tool.db;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class AesEx {

    /**
     * 密钥算法
     * java6支持56位密钥，bouncycastle支持64位
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加密/解密算法/工作模式/填充方式
     * <p>
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static byte[] iv = {0xA, 1, 0xB, 5, 4, 0xF, 7, 9,
            0x17, 3, 1, 6, 8, 0xC, 0xD, 91};

    private static final int HASH_ITERATIONS = 10000;

    private static final int KEY_LENGTH = 128;

    private static byte[] salt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            0xA, 0xB, 0xC, 0xD, 0xE, 0xF};
    private static IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
    private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

    private static Map<String, SecretKeySpec> secretKeySpecMap = new HashMap<>();

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * @param  password 系统编号
     * @return key 加密/解密密钥
     */

    private static SecretKeySpec getKey(String password) throws Exception {
        SecretKeySpec key = secretKeySpecMap.get(password);
        if (key != null) return key;
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
        SecretKey sk = keyFactory.generateSecret(keySpec);
        key = new SecretKeySpec(sk.getEncoded(), KEY_ALGORITHM);
        secretKeySpecMap.put(password, key);
        return key;
    }

    /**
     * 加密
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(byte[] content, String password) throws Exception {
        SecretKeySpec key = getKey(password);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);// 初始化
        // encrptCipher.set(cipher);
        return cipher.doFinal(content); // 加密
    }

    /**
     * 解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {
        SecretKeySpec key = getKey(password);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);// 初始化
        return cipher.doFinal(content); // 加密
    }

    /**
     * 加密成base64的字符串
     * @param content 需加密内容
     * @param password 系统编号
     * @return 加密内容
     * @throws Exception 加密异常
     */
    public static String encryptToBase64(byte[] content, String password) throws Exception {
        byte[] resultBytes = encrypt(content, password);
//        return Base64.encodeBase64URLSafeString(resultBytes);
        String base64String = Base64.encodeBase64String(resultBytes);
        return base64String.replace('+','-').replace('/', '_');
    }

    /**
     * 通过base64的字符串解密
     * @param base64Content 需解密内容
     * @param password 系统编号
     * @return 解密内容
     * @throws Exception 解密异常
     */

    public static byte[] decryptFromBase64(String base64Content, String password) throws Exception {
        String base64String = base64Content.replace('-','+').replace('_', '/');
        byte[] byteAfterDecode = Base64.decodeBase64(base64String.getBytes());
        return decrypt(byteAfterDecode, password);
    }
}
