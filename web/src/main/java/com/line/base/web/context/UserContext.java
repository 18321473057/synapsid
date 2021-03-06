package com.line.base.web.context;


import com.line.base.web.entity.IUser;

/**
 * @Description: 可以全局存取当前登录用户
 */
public class UserContext {

    /**
     * 用threadLocal存储当前登陆的用户
     */
    private static final ThreadLocal<IUser> USER_HOLDER = new ThreadLocal<IUser>();

    /**
     * 获取当前登录用户
     */
    public static IUser getCurrentUser() {
        return USER_HOLDER.get();
    }

    /**
     * 设置当前登陆用户
     */
    public static void setCurrentUser(IUser user) {
        USER_HOLDER.set(user);
    }

    /**
     * 清除用户
     */
    public static void remove() {
        USER_HOLDER.remove();
    }
}
