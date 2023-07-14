//package com.line.common.cache.redis.config;
//
//import com.line.common.cache.redis.serializer.FastJsonRedisSerializer;
//import com.line.common.cache.redis.storage.RedisCacheStorage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.net.UnknownHostException;
//import java.time.Duration;
//
///**
//* @Title AnotherRedisAutoConfiguration
//* @Package com.hoau.aquarius.common.cfg
//* @Description 第二个redis连接自动装配
//*
//* @date 2018/1/24 13:58
//* @version V1.0
//*/
//@Configuration
//@ConditionalOnProperty(prefix = "spring.redis2", name = "enable")
//@EnableConfigurationProperties(RedisBaseProperties.class)
//public class RedisBaseAutoConfiguration {
//
//    @Autowired
//    private RedisBaseProperties secondRedisProperties;
//
//    /**
//     * redis存储自动配置
//     * @param redisTemplate
//     * @return
//     *
//     * @date 2018年01月15日17:42:35
//     */
//    @Bean("secondRedisCacheStorage")
//    public RedisCacheStorage redisCacheStorage(@Qualifier("secondRedisTemplate") RedisTemplate redisTemplate) {
//        RedisCacheStorage storage = new RedisCacheStorage();
//        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashKeySerializer(serializer);
//        redisTemplate.setHashValueSerializer(serializer);
//        storage.setRedisTemplate(redisTemplate);
//        return storage;
//    }
//
//
//    /**
//     * redisTemplate自动装配
//     */
//    @Bean("secondRedisTemplate")
//    public RedisTemplate secondRedisTemplate() throws UnknownHostException {
//        RedisTemplate template = new RedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory());
//        return template;
//    }
//
//
//    /**
//     * redis连接工厂自动装配
//     */
//    public JedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
//        JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory();
//        /*
//         //连接池
//        JedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//        //IP地址
//        JedisConnectionFactory.setHostName("127.0.0.1");
//        //端口号
//        JedisConnectionFactory.setPort(6379);
//        //如果Redis设置有密码
//        //JedisConnectionFactory.setPassword(password);
//        //客户端超时时间单位是毫秒
//        JedisConnectionFactory.setTimeout(5000);
//        */
//        //pringboot 2.x版本 JedisConnectionFactory 设置连接已过时
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
//        redisStandaloneConfiguration.setHostName("127.0.0.1");
//        redisStandaloneConfiguration.setPort(6379);
//        redisStandaloneConfiguration.setDatabase(1);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("password"));
//
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
//        jedisClientConfiguration.connectTimeout(Duration.ofMillis(1800));//  connection timeout
//        JedisConnectionFactory.afterPropertiesSet();
//        return JedisConnectionFactory;
//    }
//
//    /**
//     * redis连接配置
//     * @param factory
//     *
//     * @date 2018年01月15日17:38:55
//     */
//    private void secondRedisProperties(JedisConnectionFactory factory) {
//        factory.setHostName(secondRedisProperties.getProperties().getHost());
//        factory.setPort(secondRedisProperties.getProperties().getPort());
//        factory.setPassword(secondRedisProperties.getProperties().getPassword());
//        factory.setDatabase(secondRedisProperties.getProperties().getDatabase());
//        factory.setTimeout(secondRedisProperties.getProperties().getTimeout());
//    }
//
//
//    /**
//     * redis连接池配置
//     * @return
//     *
//     * @date 2018年01月15日17:35:24
//     */
//    private JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        if (secondRedisProperties.getProperties().getPool() != null) {
//            config.setMaxTotal(secondRedisProperties.getProperties().getMaxActive());
//            config.setMaxIdle(secondRedisProperties.getProperties().getPool().getMaxIdle());
//            config.setMinIdle(secondRedisProperties.getProperties().getPool().getMinIdle());
//            config.setMaxWaitMillis(secondRedisProperties.getProperties().getPool().getMaxWait());
//        }
//        return config;
//    }
//    /**
//     * JedisPoolConfig 连接池
//     * @return
//     */
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 最大空闲数
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        // 连接池的最大数据库连接数
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        // 最大建立连接等待时间
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
//        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
//        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
//        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
//        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
//        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
//        // 在空闲时检查有效性, 默认false
//        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
//        return jedisPoolConfig;
//    }
//
//
//
//
//}
