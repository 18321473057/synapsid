package com.line.common.cache.interf;


/**
 * @Description TTL类型的缓存; 设定超时时间
 */
public interface ITTLCache<K, V> extends ICache<K, V> {
    /**
     * @return
     * @description 设置过期时间
     */
    void setTimeOut(int timeout);

}
