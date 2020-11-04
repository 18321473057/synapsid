package com.line.common.test.mybatis.service.impl;

import com.line.base.web.exception.BusinessException;
import com.line.common.test.mybatis.service.IBusinessService;
import org.springframework.stereotype.Service;

/**
 * @Author: yangcs
 * @Date: 2020/10/29 8:48
 * @Description:
 */
@Service
public class BusinessService implements IBusinessService {

    public String throwBusiness() {
        throw new BusinessException("测试 exceptionHandler注解拦截效果");
    }
}
