package com.sys.core.redis;

import java.io.Serializable;

/**
 * Redis统一出入口
 */
public class CustomRedisClient implements Serializable {
    private static CustomJedisCommands sessionJedis;
    private static CustomJedisCommands commonJedis;

    public static CustomJedisCommands getSessionJedis() {
        return sessionJedis;
    }

    public void setSessionJedis(CustomJedisCommands sessionJedis) {
        CustomRedisClient.sessionJedis = sessionJedis;
    }

    public static CustomJedisCommands getCommonJedis() {
        return commonJedis;
    }

    public void setCommonJedis(CustomJedisCommands commonJedis) {
        CustomRedisClient.commonJedis = commonJedis;
    }
}
