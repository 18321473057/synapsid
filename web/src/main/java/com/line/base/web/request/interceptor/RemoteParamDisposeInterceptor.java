package com.line.base.web.request.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.line.base.web.constans.RemoteReqConstants;
import com.line.base.web.exception.BusinessException;
import com.line.base.web.exception.RemoteBusinessException;
import com.line.base.web.login.RemoteSecurity;
import com.line.base.web.properties.SecurityProperties;
import com.line.base.web.request.RemoteRequestDto;
import com.line.base.web.request.annotation.RemoteResponse;
import com.line.common.utils.bean.MapUtils;
import com.line.common.utils.date.DateUtils;
import com.line.common.utils.encryption.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Author: yangcs
 * @Date: 2020/11/10 8:34
 * @Description: 远程请求参数处理
 * 现只处理get.post 请求; put del等请求方式 日后再写
 */
public class RemoteParamDisposeInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RemoteParamDisposeInterceptor.class);

    //默认最大时差
    private long requestTimeDelay = 180000;

    private static ApplicationContext applicationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*方法上标注@RemoteResponse,才会走自动鉴权,返回对象包装,接口防重复提交等*/
        if (!(handler instanceof HandlerMethod) || !((HandlerMethod) handler).hasMethodAnnotation(RemoteResponse.class)) {
            return true;
        }
        RemoteResponse remoteResponse = ((HandlerMethod) handler).getMethodAnnotation(RemoteResponse.class);
        /*组装请求数据对象*/
        RemoteSecurity remoteSecurity = convertValidParam(request);
        //鉴权校验等一系列校验
        disposeValidAuthentic(remoteResponse, remoteSecurity, request);
        return true;
    }


    private boolean disposeValidAuthentic(RemoteResponse remoteResponse, RemoteSecurity remoteSecurity, HttpServletRequest request) throws IOException {
        if (StringUtils.isEmpty((String) request.getAttribute(RemoteReqConstants.MESSAGE_ID))) {
            throw new BusinessException("使用@RemoteResponse注解对外提供的接口 messageId 不能为null!");
        }
        if ((long) request.getAttribute(RemoteReqConstants.TIMESTAMP) == 0) {
            throw new BusinessException("使用@RemoteResponse注解对外提供的接口 timestamp 不能为null!");
        }
        //指定不做鉴权校验
        if (!remoteResponse.AuthenticRequired()) {
            return true;
        }

        //TODO  redis校验是否重复提交


        //判断请求时间是否超时
        long timestamp = (long) request.getAttribute(RemoteReqConstants.TIMESTAMP);
        long maxTimestamp = System.currentTimeMillis();
        if (timestamp > maxTimestamp || maxTimestamp-timestamp >requestTimeDelay ) {
            throw new RemoteBusinessException("403", "鉴权失败,非法timestamp!");
        }

        //必传值校验
        if (StringUtils.isEmpty(remoteSecurity.getAppkey()) || StringUtils.isEmpty(remoteSecurity.getAuthorization()) || StringUtils.isEmpty(remoteSecurity.getSecurityBody())) {
            throw new RemoteBusinessException("403", "鉴权失败,鉴权信息不足!");
        }

        //校验签名结果是否正确,对所有请求数据进行MD5加密再与密文对比.
        logger.info("接口[{}],鉴权信息={}", request.getMethod(), JSONObject.toJSON(remoteSecurity));
        SecurityProperties securityProperties = applicationContext.getBean(SecurityProperties.class);
        boolean verifyResult = MD5Utils.verify(remoteSecurity.getSecurityBody(), remoteSecurity.getAuthorization(), securityProperties.getSecurityMap().get(remoteSecurity.getAppkey()), request.getCharacterEncoding());
        if (verifyResult) {
            return verifyResult;
        } else {
            throw new RemoteBusinessException("403", "鉴权失败,密文错误!");
        }
    }

    /**
     * 将请求信息转换成对象
     */
    private RemoteSecurity convertValidParam(HttpServletRequest request) throws IOException {
        RemoteSecurity remoteSecurity = new RemoteSecurity();

        if (request.getMethod().equals("POST")) {
            String paramsString = new String(StreamUtils.copyToByteArray(request.getInputStream()));
            RemoteRequestDto requestDto = JSON.parseObject(paramsString, RemoteRequestDto.class);
            request.setAttribute(RemoteReqConstants.MESSAGE_ID, requestDto.getMessageId());
            request.setAttribute(RemoteReqConstants.TIMESTAMP, requestDto.getTimestamp());
            remoteSecurity.setSecurityBody(paramsString);
        } else if (request.getMethod().equals("GET")) {
            request.setAttribute(RemoteReqConstants.MESSAGE_ID, request.getParameter(RemoteReqConstants.MESSAGE_ID));
            request.setAttribute(RemoteReqConstants.TIMESTAMP,
                    Long.parseLong(request.getParameter(RemoteReqConstants.TIMESTAMP) == null ? "0" : request.getParameter(RemoteReqConstants.TIMESTAMP)));
            Map params = request.getParameterMap();
            //去除空值
            remoteSecurity.setSecurityBody(MapUtils.createLinkString(MapUtils.removeEmpty(params)));
        } else {
            throw new BusinessException("使用@RemoteResponse注解标注的接口 目前只支持get,post请求!");
        }
        remoteSecurity.setAppkey(request.getHeader(RemoteReqConstants.APP_KEY));
        remoteSecurity.setAuthorization(request.getHeader(RemoteReqConstants.AUTHORIZATION));
        return remoteSecurity;
    }


    public static void main(String[] args) {

        System.out.println(new Date().getTime());
    }


}
