package com.line.base.web.request.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: yangcs
 * @Date: 2020/11/10 8:59
 * @Description:
 */
@Configuration
public class RemoteParamDisposeConfig extends WebMvcConfigurationSupport {

    @Bean
    public RemoteRequestParamInterceptor remoteParamDisposeInterceptor() {
        return new RemoteRequestParamInterceptor();
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(remoteParamDisposeInterceptor()).addPathPatterns("/**");
    }

}
