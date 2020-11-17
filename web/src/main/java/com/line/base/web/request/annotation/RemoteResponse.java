package com.line.base.web.request.annotation;


import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @Author: yangcs
 * @Date: 2020/11/7 15:30
 * @description 封装 返回远程调用请求的注解;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ResponseBody
public @interface RemoteResponse {
    //messageId/timestamp 必填校验
//    boolean paramRequired() default true;
    //鉴权校验
    boolean AuthenticRequired() default true;
}
