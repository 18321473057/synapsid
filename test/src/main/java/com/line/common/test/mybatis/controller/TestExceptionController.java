package com.line.common.test.mybatis.controller;

import com.line.base.web.controller.BaseExceptionController;
import com.line.common.test.mybatis.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangcs
 * @Date: 2020/10/29 8:43
 * @Description:
 */
@RestController
@RequestMapping("/test/e")
public class TestExceptionController extends BaseExceptionController {

    @Autowired
    private IBusinessService businessService;

    @RequestMapping("/business")
    public  String  business(){
        return businessService.throwBusiness();
    }

}
