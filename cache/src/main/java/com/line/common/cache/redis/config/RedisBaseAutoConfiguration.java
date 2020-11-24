package com.line.common.cache.redis.config;


import com.line.common.cache.redis.serializer.FastJsonRedisSerializer;
import com.line.common.cache.redis.storage.RedisCacheStorage;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: yangcs
 * @Date: 2020/11/2 17:13
 * @Description:
 */
@Configuration
@EnableConfigurationProperties({RedisConfigProperties.class, RedisPoolProperties.class})
public class RedisBaseAutoConfiguration {

    @Autowired
    private RedisConfigProperties redisConfigProperties;
    @Autowired
    private RedisPoolProperties rdisPoolProperties;

    /**
     * JedisPoolConfig 连接池
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(rdisPoolProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(rdisPoolProperties.getMinIdle());
        jedisPoolConfig.setMaxTotal(rdisPoolProperties.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(rdisPoolProperties.getMaxWaitMillis());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(rdisPoolProperties.getMinEvictableIdleTimeMillis());
        jedisPoolConfig.setNumTestsPerEvictionRun(rdisPoolProperties.getNumTestsPerEvictionRun());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(rdisPoolProperties.getTimeBetweenEvictionRunsMillis());
        jedisPoolConfig.setTestOnBorrow(rdisPoolProperties.isTestOnBorrow());
        jedisPoolConfig.setTestWhileIdle(rdisPoolProperties.isTestWhileIdle());
        return jedisPoolConfig;
    }

    /**
     * 单机版配置
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisConfigProperties.getHostName());
        redisStandaloneConfiguration.setPort(redisConfigProperties.getPort());
        redisStandaloneConfiguration.setDatabase(redisConfigProperties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConfigProperties.getPassword()));

        //获得默认的连接池构造
        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //修改我们的连接池配置
        jpcf.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * 实例化 RedisTemplate 对象
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> functionDomainRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, jedisConnectionFactory);
        return redisTemplate;
    }

    /**
     * redis存储自动配置
     */
    @Bean("redisCacheStorage")
    public RedisCacheStorage redisCacheStorage(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        RedisCacheStorage storage = new RedisCacheStorage();
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        storage.setRedisTemplate(redisTemplate);
        return storage;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", redisConfigProperties.getHostName(), redisConfigProperties.getPort());
        config.useSingleServer().setAddress(redisUrl).setPassword(redisConfigProperties.getPassword());
        config.useSingleServer().setDatabase(0);
        return Redisson.create(config);
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, JedisConnectionFactory jedisConnectionFactory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
    }
}
