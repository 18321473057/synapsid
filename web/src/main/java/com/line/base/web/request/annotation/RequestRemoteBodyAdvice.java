//package com.line.base.web.request.annotation;
//
//import com.line.base.web.constans.RemoteReqConstants;
//import com.line.base.web.exception.BusinessException;
//import com.line.base.web.request.RemoteRequestDto;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 对远程请求,进行处理;
// * 方法上必须标注@RemoteResponse,且入参要为RemoteResponseDto
// */
//@RestControllerAdvice(basePackages = {"com.line"})
//public class RequestRemoteBodyAdvice extends RequestBodyAdviceAdapter {
//    //远程请求返回信息包装注解
//    private static final Class<? extends Annotation> REMOTE_ANNOTATION_TYPE = RemoteResponse.class;
//
//    //包装注解集合
//    private static final List<Class<? extends Annotation>> ANNOTATION_TYPE_COLLCTION = new ArrayList<Class<? extends Annotation>>() {{
//        add(REMOTE_ANNOTATION_TYPE);
//    }};
//
//    /**
//     * 判断类或者方法是否使用
//     */
//    @Override
//    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        for (Class<? extends Annotation> annotationType : ANNOTATION_TYPE_COLLCTION) {
//            if (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), annotationType) || methodParameter.hasMethodAnnotation(annotationType)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
//        if (body instanceof RemoteRequestDto) {
//            RemoteRequestDto requestDto = (RemoteRequestDto) body;
//            /*参数校验*/
//            if (StringUtils.isEmpty(requestDto.getMessageId())) {
//                throw new BusinessException("@RemoteResponse注解对外提供的接口 messageId 不能为null!");
//            }
//            if (requestDto.getTimestamp() == 0) {
//                throw new BusinessException("@RemoteResponse注解对外提供的接口 timestamp 不能为null!");
//            }
//            /*messageId和timestamp 存入当前request; 以备封装返回是使用*/
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//            request.setAttribute(RemoteReqConstants.MESSAGE_ID, requestDto.getMessageId());
//            request.setAttribute(RemoteReqConstants.TIMESTAMP, requestDto.getTimestamp());
//        }
//        return body;
//    }
//
//}