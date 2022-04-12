package com.line.base.web.configuration;

import com.line.base.web.constans.UrlConstants;
import com.line.base.web.login.ContextFilter;
import com.line.base.web.login.RemoteApiFilter;
import com.line.base.web.properties.ContextFilterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Arrays;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(ContextFilterProperties.class)
public class LoginConfiguration {

    @Autowired
    private ContextFilterProperties contextFilterProperties;


    /**
     * 创建没有通过网关访问的系统使用的context过滤器，初始化RequestContext\SessionContext\UserContext
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "line.web.context", name = "enable")
    public ContextFilter contextFilter() {
        ContextFilter contextFilter = new ContextFilter();
        contextFilter.setExcludeUrlPatterns(contextFilterProperties.getExcludeUrlPatterns());
        return contextFilter;
    }

    /**
     * 构建context过滤器实例
     */
    @Bean
    @ConditionalOnBean(ContextFilter.class)
    @ConditionalOnProperty(prefix = "line.web.context", name = "enable")
    public FilterRegistrationBean contextFilterRegistration(ContextFilter contextFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(contextFilter);
        registrationBean.setUrlPatterns(Arrays.asList(UrlConstants.MATCH_ALL_URL_PATTERN));
        return registrationBean;
    }


    /**
     * 参数签名校验过滤器实例12
     */
    @Bean
    public FilterRegistrationBean aramsVerifyFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RemoteApiFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/sync/*"));
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 10000);
        return registrationBean;
    }
}
