package com.line.common.cache.redis.bloom.config;

import com.line.base.core.shutdown.ShowDownUtils;
import com.line.common.cache.redis.bloom.BloomFilterIniter;
import com.line.common.cache.redis.bloom.remote.BloomFRemote;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yangcs
 * @Date: 2020/12/7 16:09
 * @Description: 布隆过滤器初始化者 配置
 */
@Configuration
@ConditionalOnProperty(prefix = "line.bf", name = "enable")
public class BloomFilterIniterConfiguration {
    @Autowired
    private ShowDownUtils showDownUtils;
    @Autowired
    private BloomFRemote bloomFRemote;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 创建sqlSessionFactoryBean 实例
     */
    @Bean(name = "bloomFilterIniter")
    public BloomFilterIniter bloomFilterIniter() {
        BloomFilterIniter initer = new BloomFilterIniter();
        initer.setShowDownUtils(showDownUtils);
        initer.setBloomFRemote(bloomFRemote);
        initer.setRedissonClient(redissonClient);
        return initer;
    }

}