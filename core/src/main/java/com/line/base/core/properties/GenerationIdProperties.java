package com.line.base.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yangcs
 * @Date: 2020/10/14 10:14
 * @Description: id生产器 属性
 */

@Component
@ConfigurationProperties(prefix = "id.generate")
public class GenerationIdProperties {

    //工作ID (0~31)
    private Long workerId;
    //数据中心ID (0~31)
    private Long datacenterId;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }
}
