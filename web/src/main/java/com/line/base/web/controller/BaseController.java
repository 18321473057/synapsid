package com.line.base.web.controller;

import com.line.base.web.response.PageResponse;

/**
 * @Author: yangcs
 * @Date: 2020/3/26 14:32
 * @Description: 基础父控制层, 提供返回对象的封装
 */
public class BaseController {


    /**
     * @Description: 返回成功
     */
    public <T> PageResponse<T> returnSuccess() {
        return PageResponse.successResponse();
    }

    /**
     * @Description: 返回成功
     */
    public <T> PageResponse<T> returnSuccess(T obj) {
        return PageResponse.successResponse(obj);
    }


    /**
     * @param rows  列表数据
     * @param total 总条数
     * @Description: 组织返回前段数据格式:: 查询列表
     */
    public <T> PageResponse<T> returnSuccess(T rows, Long total) {
        return PageResponse.successResponse(rows, total);
    }


    /**
     * @param msg 返回消息
     * @Description: 组织返回前段数据格式
     */
    public PageResponse returnFailure(String msg) {
        return PageResponse.failureResponse(msg);
    }

    /**
     * @param code 错误代码
     * @param msg  错误信息
     * @Description: 组织返回前段数据格式
     */
    public PageResponse returnFailure(String code, String msg) {
        return PageResponse.failureResponse(code, msg);
    }

}
