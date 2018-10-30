package com.stock.common.tool.random;


import java.security.SecureRandom;

import org.apache.log4j.Logger;

/**
 * 唯 一的ID生成器，真随机数
 * 
 */
public class UniqueIdGenerator {
    private static Logger       logger = Logger.getLogger(UniqueIdGenerator.class);

    private static SecureRandom secureRandom;
    private static char[]       charList;
    private static char[]       numberList;

    static {
        String lowerStr = "abcdefghijklmnopqrstuvwxyz";
        String upperStr = lowerStr.toUpperCase();
        String numberStr = "1234567890";
        String s = lowerStr + upperStr + numberStr;
        charList = s.toCharArray();
        numberList = numberStr.toCharArray();
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            logger.error("error in UniqueIdGenerator:secureRandom", e);
        }
    }

    /**
     * 生成long型的随机数
     * 
     * @return
     */
    public static long genUniqueId() {
        long result = System.nanoTime();
        if (secureRandom != null) {
            long randomLong = secureRandom.nextLong();
            result = Math.abs(randomLong);
        }
        return result;
    }

    /**
     * 生成字母型的随机数
     * 
     * @param length
     * @return
     */
    public static String generateRandomStr(int length) {
        char[] result = new char[length];
        long random = UniqueIdGenerator.genUniqueId();
        for (int i = 0; i < length; i++) {
            int idx = (int) (random % 100 % charList.length);
            result[i] = charList[idx];
            random = random / 10;
            if (random == 0) {
                random = UniqueIdGenerator.genUniqueId();
            }
        }
        return new String(result);
    }

    /**
     * 生成数字随机数
     * 
     * @param length
     * @return
     */
    public static String generateRandomNumStr(int length) {
        char[] result = new char[length];
        long random = UniqueIdGenerator.genUniqueId();
        for (int i = 0; i < length; i++) {
            int idx = (int) (random % 100 % numberList.length);
            result[i] = numberList[idx];
            random = random / 10;
            if (random == 0) {
                random = UniqueIdGenerator.genUniqueId();
            }
        }
        return new String(result);
    }
}
