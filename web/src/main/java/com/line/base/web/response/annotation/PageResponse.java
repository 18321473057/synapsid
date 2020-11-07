package com.line.base.web.response.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
/**
 * @description 封装 分页请求返回注解;
 * 使用此注解的方法需要返回 PageHelper的Page对象
 * */
public @interface PageResponse {

}