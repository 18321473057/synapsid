package com.line.base.web.response.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.line.base.web.exception.BusinessException;
import com.line.base.web.response.AjaxResponseVo;
import com.line.base.web.response.BasicResponse;
import com.line.base.web.response.PageResponseVo;
import com.line.base.web.response.RemoteResponseDto;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 对远程请求,进行处理
 */
@RestControllerAdvice
public class RequestRemoteBodyAdvice implements RequestBodyAdvice<Object> {
    //远程请求返回信息包装注解
    private static final Class<? extends Annotation> REMOTE_ANNOTATION_TYPE = RemoteResponse.class;

    //包装注解集合
    private static final List<Class<? extends Annotation>> ANNOTATION_TYPE_COLLCTION = new ArrayList<Class<? extends Annotation>>() {{
        add(REMOTE_ANNOTATION_TYPE);
    }};

    /**
     * 判断类或者方法是否使用
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPE_COLLCTION) {
            if (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), annotationType) || methodParameter.hasMethodAnnotation(annotationType)) {
                return true;
            }
        }
        return false;


    }




    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return null;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // 防止重复包裹的问题出现
        if (body instanceof BasicResponse || body instanceof AjaxResponseVo || body instanceof PageResponseVo || body instanceof RemoteResponseDto) {
            return body;
        }

        //处理 RemoteResponse 注解
        if (methodParameter.hasMethodAnnotation(REMOTE_ANNOTATION_TYPE)) {
//            methodParameter
//            return this.disposeRemoteAnnotation(body, methodParameter, request);
        }

        return body;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return null;
    }
}