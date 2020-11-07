package com.line.base.web.response.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.line.base.web.exception.BusinessException;
import com.line.base.web.response.AjaxResponse;
import com.line.base.web.response.BasicResponse;
import com.line.base.web.response.PageResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {
    //ajax返回信息包装注解
    private static final Class<? extends Annotation> AJAX_ANNOTATION_TYPE = AjaxResponse.class;
    //分页返回信息包装注解
    private static final Class<? extends Annotation> PAGE_ANNOTATION_TYPE = PageResponse.class;

    //包装注解集合
    private static final List<Class<? extends Annotation>> ANNOTATION_TYPE_COLLCTION = new ArrayList<Class<? extends Annotation>>() {{
        add(AJAX_ANNOTATION_TYPE);
        add(PAGE_ANNOTATION_TYPE);
    }};

    /**
     * 判断类或者方法是否使用了 @ResponseResultBody
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPE_COLLCTION) {
            if (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), annotationType) || returnType.hasMethodAnnotation(annotationType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 当类或者方法使用了 @ResponseResultBody 就会调用这个方法
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 防止重复包裹的问题出现
        if (body instanceof BasicResponse) {
            return body;
        }

        if (returnType.hasMethodAnnotation(AJAX_ANNOTATION_TYPE)) {
            AjaxResponse ajaxAnnotation = (AjaxResponse) returnType.getMethodAnnotation(AJAX_ANNOTATION_TYPE);
            /*
             * ajaxAnnotation注解标注的方法可能会返回 String;返回的body是String的话会先执行SpringMVC的StringHttpMessageConverter,而不是我们自定义的beforeBodyWrite;
             * 这里需要单独处理,将ajaxAnnotation转成String;
             * */
            if (body instanceof String) {
                ObjectMapper om = new ObjectMapper();
                try {
                    return om.writeValueAsString(ajaxAnnotation.success() ? AjaxResponseVo.success(body) : AjaxResponseVo.error(body));
                } catch (JsonProcessingException e) {
                    throw new BusinessException("ObjectMapper.writeValueAsString();统一处理返回对象中,自定义转换失败!e={}",e.getMessage());
                }
            }
            return ajaxAnnotation.success() ? AjaxResponseVo.success(body) : AjaxResponseVo.error(body);
        }

        if (returnType.hasMethodAnnotation(PAGE_ANNOTATION_TYPE)) {
            //PageResponse 不会返回String , 不需要单独处理;
            if (body instanceof Page) {
                return PageResponseVo.success((Page) body);
            }
            if (body instanceof PageInfo) {
                return PageResponseVo.success((PageInfo) body);
            }
            throw new BusinessException("使用注解PageResponse,染回的对象必须属于PageHelper中[Page,PageInfo]的一员");
        }
        return body;
    }
}  