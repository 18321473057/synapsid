package com.line.common.test.test;


/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: crms项目前段页面统一返回对象
 */
public class Response<T> extends ResponseBaseDto {

    //成功与否
    private Boolean success;
    //列表数据
    private T rows;
    //分页总行数
    private Long total;
    //返回信息
    private String result;
    //对象数据 只为更好的可读性
    private T obj;


    /**
     * @Description: 构建成功信息
     */
    public static <T> Response<T> buildSuccessResponse(String msg) {
        return new Response<T>(Boolean.TRUE, msg);
    }

    /**
     * @param rows  列表数据
     * @param total 总条数
     * @Description: 构建成功信息
     */
    public static <T> Response<T> buildSuccessResponse(T rows, Long total) {
        return new Response<T>(Boolean.TRUE, rows, total);
    }

    /**
     * @param obj 返回对象
     * @Description: 构建成功信息
     */
    public static <T> Response<T> buildSuccessResponse(T obj) {
        return new Response<T>(Boolean.TRUE, obj);
    }

    /**
     * @param obj 返回对象
     * @Description: 构建成功信息
     */
    public static <T> Response<T> buildSuccessResponse(T obj, String msgId) {
        return new Response<T>(Boolean.TRUE, obj, msgId);
    }

    /**
     * @param msg 返回消息
     * @Description: 构建失败消息
     */
    public static Response buildFailureResponse(String msg) {
        return new Response(Boolean.FALSE, msg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Response() {

    }

    public Response(Boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public Response(Boolean success, T obj) {
        this.success = success;
        this.obj = obj;
    }

    public Response(Boolean success, T obj, String msgId) {
        this.success = success;
        this.obj = obj;
        super.setMessageId(msgId);
    }


    public Response(Boolean success, T rows, Long total) {
        this.success = success;
        this.rows = rows;
        this.total = total;
    }

}
