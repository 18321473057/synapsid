package com.line.common.cache.redis.bloom;

import java.io.Serializable;

/**
 * @Description 布隆过滤器配置异常
 */
public class BloomConfigException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 5034071316267004906L;

    public BloomConfigException(String msg) {
        super(msg);
    }

}
