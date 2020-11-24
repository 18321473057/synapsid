package com.line.base.web.login;


import com.alibaba.fastjson.JSON;
import com.line.base.web.constans.RemoteReqConstants;
import com.line.base.web.properties.RemoteSecurityProperties;
import com.line.base.web.request.RemoteRequestDto;
import com.line.common.utils.bean.MapUtils;
import com.line.common.utils.encryption.MD5Utils;
import org.apache.commons.lang3.StringUtils;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1:该过滤器只做接口鉴权和保护使用,不做自动封装返回对象逻辑;
 * 自动封装返回对象逻辑写在RemoteParamDisposeInterceptor和@RemoteResponse
 * <p>
 * 2:该过滤器并未生效,如要使用请另写一个过滤器来继承并加入拦截器组;加入拦截器组代码如下
 */

//@Bean
//public FilterRegistrationBean aramsVerifyFilterRegistration() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new RemoteApiFilter());
//        registrationBean.setUrlPatterns(Arrays.asList("/sync/*"));
//        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 10000);
//        return registrationBean;
//        }

public class RemoteApiFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RemoteApiFilter.class);

    /**
     * 允许的请求延迟时间(秒), 默认3分钟
     */
    protected long requestTimeDelaySeconds = 180;
    /**
     * 排除校验的地址
     */
    private List<String> excludeUrlPatterns = new ArrayList(Arrays.asList("/sync/*"));

    @Autowired
    private RemoteSecurityProperties properties;
    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private RedissonClient redisson;

    AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = request.getServletPath();
        boolean matched = false;
        for (String pattern : excludeUrlPatterns) {
            matched = antPathMatcher.match(pattern, url);
            if (matched) {
                break;
            }
        }
        return matched;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RemoteSecurityInfo securityInfo = new RemoteSecurityInfo();
        securityInfo.setApiKey(request.getHeader("apiKey"));
        securityInfo.setAuthorization(request.getHeader("token"));
        securityInfo.setHttpMethod(request.getMethod());
        securityInfo.setRequestUri(request.getRequestURI());

        //鉴权参数校验
        if (StringUtils.isEmpty(securityInfo.getApiKey()) || StringUtils.isEmpty(securityInfo.getAuthorization())
                || StringUtils.isEmpty(securityInfo.getHttpMethod()) || StringUtils.isEmpty(securityInfo.getRequestUri())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            logger.error("鉴权信息不足,httpMethod=[{}],requestUri=[{}],apiKey=[{}],token=[{}]", securityInfo.getHttpMethod(), securityInfo.getRequestUri(),
                    securityInfo.getApiKey(), securityInfo.getAuthorization());
            return;
        }

        //组装鉴权信息对象
        if (HttpMethod.GET.toString().equals(securityInfo.getHttpMethod())) {
            Map params = request.getParameterMap();
            securityInfo.setSecurityBodyStr(MapUtils.createLinkString(MapUtils.removeEmpty(params)));
            securityInfo.setTimestamp(Long.parseLong(StringUtils.isEmpty(request.getParameter("timestamp")) ? "0" : request.getParameter("timestamp")));
            securityInfo.setMessageId(request.getParameter("messageId"));
        } else if (HttpMethod.POST.toString().equals(securityInfo.getHttpMethod())) {
            String paramsString = new String(StreamUtils.copyToByteArray(request.getInputStream()));
            securityInfo.setSecurityBodyStr(paramsString);
            RemoteRequestDto requestDto = JSON.parseObject(paramsString, RemoteRequestDto.class);
            securityInfo.setTimestamp(requestDto.getTimestamp());
            securityInfo.setMessageId(requestDto.getMessageId());
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            logger.error("httpMethod=[{}],requestUri=[{}],只支持get.post请求方式", securityInfo.getHttpMethod(), securityInfo.getRequestUri());
            return;
        }

        //请求身份标识参数校验
        if (securityInfo.getTimestamp() == 0 || StringUtils.isEmpty(securityInfo.getMessageId())) {
            logger.error("鉴权信息不足,requestUri=[{}],timestamp=[{}],messageId=[{}]", securityInfo.getRequestUri(), securityInfo.getTimestamp(), securityInfo.getMessageId());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        //判断请求时间是否超时
        long currentTimeMillis = System.currentTimeMillis();
        long differSeconds = currentTimeMillis - securityInfo.getTimestamp();
        if (differSeconds < 0 || differSeconds > requestTimeDelaySeconds) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            logger.error("请求地址[{}],请求时间超时,timestamp=[{}],currentTimeMillis=[{}]", securityInfo.getRequestUri(), securityInfo.getTimestamp(), currentTimeMillis);
            return;
        }

        //TODO  redis锁校验是否重复提交,(这里要使用redis锁)
        String rediskey = request.getRequestURI() + request.getAttribute(RemoteReqConstants.MESSAGE_ID) + request.getAttribute(RemoteReqConstants.TIMESTAMP);
//        RLock lock = redisson.getLock("m");
//        lock.
//        lock.lock(requestTimeDelaySeconds,TimeUnit.SECONDS);

//        if (o != null) {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            logger.error("请求地址[{}],请求时间超时,timestamp=[{}],currentTimeMillis=[{}]", securityInfo.getRequestUri(), securityInfo.getTimestamp(), currentTimeMillis);
//
//        }
        redisTemplate.opsForValue().set(rediskey, "true", requestTimeDelaySeconds, TimeUnit.MILLISECONDS);

        //获取秘钥
        String secureKey = properties.getSecurityMap().get(securityInfo.getApiKey());
        if (StringUtils.isEmpty(secureKey)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            logger.error("鉴权失败,未能获取秘钥,请求地址[{}],,apiKey=[{}]", securityInfo.getRequestUri(), securityInfo.getApiKey());
            return;
        }
        String signStr = securityInfo.getRequestUri() + "|" + securityInfo.getSecurityBodyStr() + "|" + securityInfo.getApiKey() + "|";

        logger.info("请求地址[{}],鉴权信息:paramsString=[{}]", securityInfo.getRequestUri(), signStr + secureKey);
        logger.info("请求地址[{}],鉴权信息:token=[{}]", securityInfo.getRequestUri(), securityInfo.getAuthorization());
        logger.info("请求地址[{}],鉴权信息:characterEncoding=[{}]", securityInfo.getRequestUri(), request.getCharacterEncoding());


        //校验签名结果是否正确
        boolean verifyResult = MD5Utils.verify(signStr, securityInfo.getAuthorization(), secureKey, request.getCharacterEncoding());
        if (verifyResult) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
    }

}
