package com.line.base.core.configuration;

import com.line.base.core.generate.SnowflakeIdWorker;
import com.line.base.core.properties.GenerationIdProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description id生成器配置以及自动装配
 */
@Configuration
@ConditionalOnProperty(prefix = "id.generate", name = "enable")
@EnableConfigurationProperties(GenerationIdProperties.class)
public class GenerationIdAutoConfiguration {

    //id生产器 属性
    @Autowired
    private GenerationIdProperties generationIdProperties;

    /**
     * @return
     * @throws NullPointerException
     * @description 雪花算法ID生成器自动装配
     */
    @Bean(name = "snowflakeIdWorker")
    public SnowflakeIdWorker snowflakeIdWorker() throws NullPointerException {
        if (generationIdProperties == null) {
            throw new NullPointerException("GenerationIdProperties is null!");
        }
        if (generationIdProperties.getDatacenterId() == null) {
            throw new NullPointerException("GenerationIdProperties.datacenterId is null!");
        }
        if (generationIdProperties.getWorkerId() == null) {
            throw new NullPointerException("GenerationIdProperties.workerId is null!");
        }
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(generationIdProperties.getWorkerId(), generationIdProperties.getDatacenterId());
        return snowflakeIdWorker;
    }
}
