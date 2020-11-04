package com.line.base.web.context;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @Description: SessionContext，可获取、操作当前请求的session，存储当前登录的用户id
 */
public class SessionContext {

    /**
     * session attribute中存放用户信息的key
     */
    public static final String SESSION_USER_ID_ATTRIBUTE_NAME = "APP_SESSION_USER_ID_ATTRIBUTE_KEY";

    /**
     * session attribute中存放local信息的key
     */
    public static final String SESSION_LOCAL_ATTRIBUTE_NAME = "APP_SESSION_LOCAL_ATTRIBUTE_KEY";

    /**
     * 获取session,没有则创建新的
     */
    public static HttpSession getSession() {
        return getSession(true);
    }

    /**
     * 按条件后去session
     */
    public static HttpSession getSession(boolean create) {
        return RequestContext.getRequest().getSession(create);
    }

    /**
     * 向session中增加属性
     */
    public static void setAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    /**
     * 从session中获取属性
     */
    public static Object getAttribute(String name) {
        HttpSession session = getSession(false);
        if (session != null) {
            return session.getAttribute(name);
        }
        return null;
    }

    /**
     * 设置当前登录用户id
     */
    public static void setCurrentUserId(String userId) {
        setAttribute(SESSION_USER_ID_ATTRIBUTE_NAME, userId);
    }

    /**
     * 获取当前登录用户id
     */
    public static String getCurrentUserId() {
        return (String) getAttribute(SESSION_USER_ID_ATTRIBUTE_NAME);
    }

    /**
     * 设置方言
     */
    public static void setCurrentLocal(Locale local) {
        setAttribute(SESSION_LOCAL_ATTRIBUTE_NAME, local);
    }

    /**
     * 获取方言
     */
    public static Locale getCurrentLocal() {
        return (Locale) getAttribute(SESSION_LOCAL_ATTRIBUTE_NAME);
    }

    /**
     * 销毁session
     */
    public static void invalidateSession() {
        HttpSession session = getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
