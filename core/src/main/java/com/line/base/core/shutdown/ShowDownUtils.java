package com.line.base.core.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: yangcs
 * @Date: 2020/12/5 15:37
 * @Description:
 */
@Component
public class ShowDownUtils {

    @Autowired
    private ApplicationContext context;

    private static final Logger logger = LoggerFactory.getLogger(ShowDownUtils.class);


    /**
     * 项目启动是 必须初始化的代码 发生错误;可以终止项目启动
     */
    public void stop(Exception e) {
        logger.error(">>>>>>>> 项目启动失败 <<<<<<<<<<<");
        e.printStackTrace();
        ((ConfigurableApplicationContext) context).close();
    }

}
