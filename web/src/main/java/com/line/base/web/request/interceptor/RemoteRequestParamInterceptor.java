package com.line.base.web.request.interceptor;

import com.alibaba.fastjson.JSON;
import com.line.base.core.exception.BusinessException;
import com.line.base.web.constans.RemoteReqConstants;
import com.line.base.web.request.RemoteRequestDto;
import com.line.base.web.request.annotation.RemoteResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yangcs
 * @Date: 2020/11/10 8:34
 * @Description: 处理 RemoteResponse 注解, 在request中添加messageId 和timestamp;
 * 以被后面代码使用   class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object>
 */
public class RemoteRequestParamInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*方法上标注@RemoteResponse,才会自动封装返回对象*/
        if (!(handler instanceof HandlerMethod) || !((HandlerMethod) handler).hasMethodAnnotation(RemoteResponse.class)) {
            return true;
        }

        //之前已经被设置了 , 直接放行
        if (request.getAttribute(RemoteReqConstants.MESSAGE_ID) != null && request.getAttribute(RemoteReqConstants.TIMESTAMP) != null) {
            return true;
        }

        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            String paramsString = new String(StreamUtils.copyToByteArray(request.getInputStream()));
            RemoteRequestDto requestDto = JSON.parseObject(paramsString, RemoteRequestDto.class);
            request.setAttribute(RemoteReqConstants.MESSAGE_ID, requestDto.getMessageId());
            request.setAttribute(RemoteReqConstants.TIMESTAMP, requestDto.getTimestamp() == 0 ? null : requestDto.getTimestamp());
        } else if (request.getMethod().equals(HttpMethod.GET.toString())) {
            request.setAttribute(RemoteReqConstants.MESSAGE_ID, request.getParameter(RemoteReqConstants.MESSAGE_ID));
            request.setAttribute(RemoteReqConstants.TIMESTAMP, request.getParameter(RemoteReqConstants.TIMESTAMP) == null ? null : request.getParameter(RemoteReqConstants.TIMESTAMP));
        } else {
            throw new BusinessException("使用@RemoteResponse注解标注的接口 目前只支持get,post请求!");
        }

        //之前已经被设置了 , 直接放行
        if (request.getAttribute(RemoteReqConstants.MESSAGE_ID) == null && request.getAttribute(RemoteReqConstants.TIMESTAMP) == null) {
            throw new BusinessException("使用@RemoteResponse注解对外提供的接口 messageId或timestamp 不能为null!");
        }
        return true;
    }

}


