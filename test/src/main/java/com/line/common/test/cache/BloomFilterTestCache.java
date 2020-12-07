package com.line.common.test.cache;

import com.line.common.cache.redis.DefaultBloomFilterCache;
import com.line.common.cache.redis.DefaultTTLRedisCache;
import com.line.common.cache.redis.storage.RedisCacheStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterTestCache extends DefaultBloomFilterCache {

    @Autowired
    private RedisCacheStorage redisCacheStorage;
    @Autowired
    private UserCacheProvider userCacheProvider;

    public static final String UUID = BloomFilterTestCache.class.getName();

    public String getUUID() {
        return UUID;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        setCacheStorage(redisCacheStorage);
        setCacheProvider(userCacheProvider);
    }
}
