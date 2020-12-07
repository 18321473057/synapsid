package com.line.common.test.cache;

import com.line.base.core.dto.AjaxResponseDto;
import com.line.base.core.shutdown.ShowDownUtils;
import com.line.common.cache.redis.DefaultTTLRedisCache;
import com.line.common.cache.redis.bloom.BloomManager;
import com.line.common.cache.redis.bloom.exception.BloomConfigException;
import com.line.common.cache.redis.bloom.remote.BloomFRemote;
import com.line.common.cache.redis.storage.RedisCacheStorage;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends DefaultTTLRedisCache<UserDTO> {

    @Autowired
    private RedisCacheStorage redisCacheStorage;
    @Autowired
    private UserCacheProvider userCacheProvider;
    @Autowired
    private ShowDownUtils showDownUtils;

    @Autowired
    public BloomFRemote bloomFRemote;

    public static final String UUID = UserCache.class.getName();

    public String getUUID() {
        return UUID;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //先配置 过滤器

        AjaxResponseDto<RBloomFilter> ajax = bloomFRemote.getBloomFilter(getUUID());
        if (!ajax.getSuccess()|| ajax.getObj() == null) {
            showDownUtils.stop(new BloomConfigException("名为" + getUUID() + "的过滤器,获取失败!"));
        }
        BloomManager.getInstance().register(ajax.getObj());
        super.afterPropertiesSet();
        setCacheStorage(redisCacheStorage);
        setCacheProvider(userCacheProvider);
    }
}
