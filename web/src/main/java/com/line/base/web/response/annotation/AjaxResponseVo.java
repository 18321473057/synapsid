package com.line.base.web.response.annotation;


import com.line.base.web.response.BasicResponse;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 普通ajax请求返回对象
 */
public class AjaxResponseVo<T> extends BasicResponse<T> {
    //ajax请求返回的对象
    protected T obj;

    public static <T> AjaxResponseVo<T> success(T t) {
        AjaxResponseVo response = getScuuess(AjaxResponseVo.class);
        response.setObj(t);
        return response;
    }

    public static <T> AjaxResponseVo<T> error() {
        return getError(AjaxResponseVo.class);
    }

    public static <T> AjaxResponseVo<T> error(T t) {
        AjaxResponseVo response = getScuuess(AjaxResponseVo.class);
        response.setObj(t);
        return response;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
