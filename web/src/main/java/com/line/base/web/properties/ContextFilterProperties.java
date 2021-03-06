package com.line.base.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 陈宇霖
 * @version V1.0
 * @Title: ContextFilterProperties
 * @Package com.hoau.zodiac.springboot.autoconfig.context
 * @Description: context过滤器配置参数
 * @date 2017/8/17 09:47
 */
@ConfigurationProperties(prefix = "line.web.context")
public class ContextFilterProperties {

    /**
     * 是否启用过滤器
     */
    private boolean enable;


    /**
     * 不进行过滤的地址列表
     */
    private List<String> excludeUrlPatterns;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }
}
