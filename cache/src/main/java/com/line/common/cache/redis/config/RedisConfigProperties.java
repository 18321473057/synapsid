package com.line.common.cache.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description redis配置类
 */
@ConfigurationProperties(prefix = "spring.redis.config")
public class RedisConfigProperties {
    private String hostName;
    private int port;
    private int database;
    private String password;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
