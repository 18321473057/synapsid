package com.line.base.web.context;

/**
 * @Author: yangcs
 * @Date: 2020/11/10 12:42
 * @Description:
 */
public enum MethodEnum {

    GET("GET"), POST("POST");

    public String val;

    MethodEnum(String val) {
        this.val = val;
    }


}
