package com.line.common.test.resposttest;

import com.line.base.web.response.annotation.AjaxResponse;
import com.line.base.web.response.AjaxResponseVo;
import com.line.common.test.mybatis.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: yangcs
 * @Date: 2020/10/29 8:43
 * @Description:
 */
@Component
@RequestMapping("/test/e")
public class TestExceptionController {

    @Autowired
    private IBusinessService businessService;

    @RequestMapping("/business")
    @AjaxResponse
    public String business() {
        return businessService.throwBusiness();
    }


    //没有按照json格式返回 会报错
    @RequestMapping("/foo")
    public AjaxResponseVo businessfoo() {
        return AjaxResponseVo.error("没有@responseBody和restController,404");
    }

}
