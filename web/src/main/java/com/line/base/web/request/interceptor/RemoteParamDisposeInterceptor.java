package com.line.base.web.request.interceptor;

import com.line.base.web.constans.RemoteReqConstants;
import com.line.base.web.context.MethodEnum;
import com.line.base.web.exception.BusinessException;
import com.line.base.web.exception.RemoteBusinessException;
import com.line.base.web.request.RemoteRequestDto;
import com.line.base.web.request.annotation.RemoteResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/11/10 8:34
 * @Description: 远程请求参数处理
 * 现只处理get.post 请求; put del等请求方式 日后再写
 */
public class RemoteParamDisposeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*方法上标注@RemoteResponse,才会走自动鉴权,返回对象包装,接口防重复提交等*/
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (!((HandlerMethod) handler).hasMethodAnnotation(RemoteResponse.class)) {
            return true;
        }

        RemoteResponse remoteResponse = ((HandlerMethod) handler).getMethodAnnotation(RemoteResponse.class);
        /*请求对象RemoteRequestDto 分装或获取 */
        RemoteRequestDto requestDto = null;
        if (request.getMethod().equals(MethodEnum.POST.val)) {
            requestDto = convertPost(handler);
        } else if (request.getMethod().equals(MethodEnum.GET.val)) {
            requestDto = convertGet(request);
        }

        /*对必填信息做非空校验*/
        disposeValidParam(requestDto, remoteResponse, request);

        //鉴权校验
        disposeValidAuthentic(requestDto, remoteResponse);

        return true;
    }

    private void disposeValidAuthentic(RemoteRequestDto requestDto, RemoteResponse remoteResponse) {
        if (remoteResponse.AuthenticRequired()) {
            //排序
        }
    }

    private void disposeValidParam(RemoteRequestDto requestDto, RemoteResponse remoteResponse, HttpServletRequest request) {
        if (remoteResponse.paramRequired()) {
            if (StringUtils.isEmpty(requestDto.getMessageId())) {
                throw new BusinessException("@RemoteResponse注解对外提供的接口 messageId 不能为null!");
            }
            if (requestDto.getTimestamp() == 0) {
                throw new BusinessException("@RemoteResponse注解对外提供的接口 timestamp 不能为null!");
            }
            request.setAttribute(RemoteReqConstants.MESSAGE_ID, requestDto.getMessageId());
            request.setAttribute(RemoteReqConstants.TIMESTAMP, requestDto.getTimestamp());
        }
    }

    private RemoteRequestDto convertGet(HttpServletRequest request) {
        RemoteRequestDto requestDto = new RemoteRequestDto();
        requestDto.setMessageId(request.getParameter(RemoteReqConstants.MESSAGE_ID));
        requestDto.setTimestamp(Long.parseLong(request.getParameter(RemoteReqConstants.TIMESTAMP) == null ? "0" : request.getParameter(RemoteReqConstants.TIMESTAMP)));
        requestDto.setData(request.getParameterMap());
        return requestDto;
    }

    private RemoteRequestDto convertPost(Object handler) {
        if (handler instanceof List) {
            //多参数handler查找RemoteRequestDto 对象
            List objectList = (List) handler;
            for (Object obj : objectList) {
                if (obj instanceof RemoteRequestDto) {
                    return (RemoteRequestDto) obj;
                }
            }
            throw new RemoteBusinessException("500", "@RemoteResponse标注的POST请求方法,入参必须包含RemoteRequestDto");
        } else if (handler instanceof RemoteRequestDto) {
            return (RemoteRequestDto) handler;
        } else {
            throw new RemoteBusinessException("500", "@RemoteResponse标注的POST请求方法,入参必须包含RemoteRequestDto");
        }

//        request.


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
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        if (list instanceof List) {
            System.out.println("List");
        }
        if (list instanceof ArrayList) {
            System.out.println("arrayList");
        }
    }


}
