package com.line.base.web.controller;

import com.line.base.web.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yangcs
 * @Date: 2020/3/28 15:29
 * @Description: 部分的异常处理controller  不是全局处理,只有继承的类抛出自定义的异常 才会处理
 */
@Component
public class BaseExceptionController extends BaseController {
//    private static final Logger LOGGER = LogManager.getLogger(BaseExceptionController.class);

    //异常处理逻辑
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PageResponse handlerException(Exception exception) throws Exception {
        if (exception instanceof BusinessException) {
//            LOGGER.error("自定义异常被捕捉,e ={}", exception);
            //自定义异常,自己写处理逻辑
            return returnFailure(((BusinessException) exception).getCode(),exception.getMessage());
        } else {
            //未捕获异常,不予处理,照常抛出
            throw exception;
        }
    }
}
