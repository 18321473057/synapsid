package com.line.common.test.project;

import com.line.common.linestartermaile.service.IMailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: yangcs
 * @Date: 2021/1/13 17:05
 * @Description: 自定义项目组件 交由springBoot 扫描自动配置注入
 */
@Component
public class ProjectTest {
    @Autowired
    private IMailSendService mailSendService;
    public void send(String msg) {
        mailSendService.send(msg);
    }
}
