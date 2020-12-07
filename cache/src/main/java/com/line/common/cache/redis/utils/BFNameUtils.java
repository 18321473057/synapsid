package com.line.common.cache.redis.utils;

/**
 * @Author: yangcs
 * @Date: 2020/12/7 11:16
 * @Description:
 */
public class BFNameUtils {

    public static String getBfName(String redisUUID) {
        return redisUUID + "-RBF";
    }

}
