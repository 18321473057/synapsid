package com.line.base.web.exception;

/**
 * @Author: yangcs
 * @Date: 2020/4/14 15:43
 * @Description: 自定义业务异常;
 *  业务逻辑校验失败抛出,由BaseExceptionController  @ExceptionHandler(Exception.class) 方法捕捉 并组装成json 信息返回
 */
public class BusinessException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException( String code,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException() {

    }
}
