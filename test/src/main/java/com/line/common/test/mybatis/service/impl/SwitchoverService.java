package com.line.common.test.mybatis.service.impl;

import com.line.base.datasource.dynamic.AnotherDatasource;
import com.line.common.test.mybatis.service.IMybatisService;
import com.line.common.test.mybatis.service.ISwitchoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: yangcs
 * @Date: 2020/10/27 14:04
 * @Description:
 */
@Service
public class SwitchoverService implements ISwitchoverService {
    @Autowired
    private IMybatisService mybatisService;


    /**
     * @warning  注解只在service入口方法生效 ,  想要切换事务,只能再写一个service
     * 在一个事务中 , 还是切换不了数据源!!!!!!!!!!!!!!!!!!!!!!!!!!
     * */
//    @Transactional
    public String selectAnyAll() {
        String a = mybatisService.selectAnyMaster();
        String b = mybatisService.selectAnySlave();
        return a + "-" + b;
    }


}
