package com.line.common.cache.interf;

/**
 * @Description 定时失效缓存数据提供者
 */
public interface ITTLCacheProvider<V> {

    /**
     * 加载单个元素
     *
     * @param key
     * @return V
     * @since:
     */
    V get(String key);

}
