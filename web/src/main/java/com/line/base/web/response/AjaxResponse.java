package com.line.base.web.response;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
/**
 * @description 封装 普通ajax请求返回对象;
 * */
public @interface AjaxResponse {

    boolean success() default true;

}  