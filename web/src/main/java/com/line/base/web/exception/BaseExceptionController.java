package com.line.base.web.exception;

import com.line.base.web.response.AjaxResponseVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: yangcs
 * @Date: 2020/3/28 15:29
 * @Description: 部分的异常处理controller  不是全局处理,只有继承的类抛出自定义的异常 才会处理
 */
@RestControllerAdvice
public class BaseExceptionController {

    /**
     * 异常处理逻辑
     */
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception exception) throws Exception {
        if (exception instanceof BusinessException) {
            //本地业务异常,返回状态500,以及异常信息;
            return  AjaxResponseVo.error(exception.getMessage());
        } else if (exception instanceof Exception) {
            //这里处理远程调用异常
            throw exception;
        } else {
            throw exception;
        }
    }
}
