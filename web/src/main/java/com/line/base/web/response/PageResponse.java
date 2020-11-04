package com.line.base.web.response;


/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: crms项目前段页面统一返回对象
 */
public class PageResponse<T> extends BaseResponse<T> {

    //列表数据
    private T rows;
    //分页总行数
    private long total;
    //对象数据 只为更好的可读性
    private T obj;


    /**
     * @param rows  列表数据
     * @param total 总条数
     * @Description: 构建成功信息
     */
    public static <T> PageResponse<T> successResponse(T rows, long total) {
        PageResponse<T> resp = successResponse();
        resp.rows = rows;
        resp.total = total;
        return resp;
    }

    /**
     * @param obj 对象数据
     * @Description: 构建成功信息
     */
    public static <T> PageResponse<T> successResponse(T obj) {
        PageResponse<T> resp = successResponse();
        resp.obj = obj;
        return resp;
    }

    /**
     * @param msg 错误信息
     * @Description: 构建失败信息
     */
    public static <T> PageResponse<T> failureResponse(String msg) {
        PageResponse<T> resp = failureResponse();
        resp.msg = msg;
        return resp;
    }

    /**
     * @param  code 错误代码
     * @param msg 错误信息
     * @Description: 构建失败信息
     */
    public static <T> PageResponse<T> failureResponse(String code, String msg) {
        PageResponse<T> resp = failureResponse(msg);
        resp.code = code;
        return resp;
    }


    //成功对象
    public static <T> PageResponse<T> successResponse() {
        PageResponse<T> resp = new PageResponse<T>();
        resp.success = true;
        return resp;
    }

    //失败对象
    public static <T> PageResponse<T> failureResponse() {
        PageResponse<T> resp = new PageResponse<T>();
        resp.success = true;
        return resp;
    }


    public PageResponse() {
    }


    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
