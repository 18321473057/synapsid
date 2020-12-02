//package com.line.common.test.cache;
//
//import com.line.common.cache.redis.DefaultTTLRedisCache;
//import com.line.common.cache.redis.storage.RedisCacheStorage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserCache extends DefaultTTLRedisCache<UserDTO> {
//
//    @Autowired
//    private RedisCacheStorage redisCacheStorage;
//
//    @Autowired
//    private UserCacheProvider userCacheProvider;
//
//    public static final String UUID = UserCache.class.getName();
//
//    public String getUUID() {
//        return UUID;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        super.afterPropertiesSet();
//        setCacheStorage(redisCacheStorage);
//        setCacheProvider(userCacheProvider);
//    }
//}
