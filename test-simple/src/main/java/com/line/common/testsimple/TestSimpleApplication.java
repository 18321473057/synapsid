package com.line.common.testsimple;

import com.line.common.linestartermaile.service.impl.MailSendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestSimpleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestSimpleApplication.class, args);
        MailSendService bean = applicationContext.getBean(MailSendService.class);
        bean.send("XXA");
    }

}
