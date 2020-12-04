package com.line.base.web.response;


/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 普通ajax请求返回对象
 */
public class AjaxResponseDto<T> extends BasicResponse<T> {
    //ajax请求返回的对象
    protected T obj;

    public static <T> AjaxResponseDto<T> success(T t) {
        AjaxResponseDto response = getScuuess(AjaxResponseDto.class);
        response.setObj(t);
        return response;
    }

    public static <T> AjaxResponseDto<T> error() {
        return getError(AjaxResponseDto.class);
    }

    public static <T> AjaxResponseDto<T> error(T t) {
        AjaxResponseDto response = getError(AjaxResponseDto.class);
        if (t instanceof String) {
            //返回的是错误提示
            response.setMsg((String) t);
        } else {
            //返回的是错误对象
            response.setObj(t);
        }
        return response;
    }

    public static <T> AjaxResponseDto<T> error(String code, T t) {
        AjaxResponseDto response = getError(AjaxResponseDto.class);
        response.setCode(code);
        if (t instanceof String) {
            //返回的是错误提示
            response.setMsg((String) t);
        } else {
            //返回的是错误对象
            response.setObj(t);
        }
        return response;
    }


    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
