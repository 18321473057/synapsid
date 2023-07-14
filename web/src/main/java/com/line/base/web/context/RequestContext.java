package com.line.base.web.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: RequstContext request上下文操作 存放本次请求Id 和用户id
 */
public class RequestContext {

    /**
     * request中存储requestId的key
     */
    public static final String REQUEST_ID_ATTRIBUTE_NAME = "APP_REQUEST_ID_ATTRIBUTE_KEY";

    /**
     * request中存储USERID的key
     */
    public static final String REQUEST_USER_ID_ATTRIBUTE_NAME = "APP_REQUEST_USER_ID_ATTRIBUTE_KEY";

    /**
     * 私有构造方法，不允许实例化
     */
    private RequestContext() {
    }

    /**
     * 全局获取request的方法
     */
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取RequestId
     */
    public static String getRequestId() {
        return (String) getRequest().getAttribute(RequestContext.REQUEST_ID_ATTRIBUTE_NAME);
    }

    /**
     * 设置requestId
     */
    public static void setRequestId(String requestId) {
        getRequest().setAttribute(RequestContext.REQUEST_ID_ATTRIBUTE_NAME, requestId);
    }

    /**
     * 获取当前登录的用户Id
     */
    public static String getCurrentUserId() {
        return (String) getRequest().getAttribute(RequestContext.REQUEST_USER_ID_ATTRIBUTE_NAME);
    }

    /**
     * 设置当前登录的用户id
     */
    public static void setCurrentUserId(String userId) {
        getRequest().setAttribute(RequestContext.REQUEST_USER_ID_ATTRIBUTE_NAME, userId);
    }

}
