package com.line.common.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import tk.mybatis.spring.annotation.MapperScan;

//scanBasePackages="com.line" 自动装配代码 只需要被包扫描到
//他妈的 设置之后就没有默认的扫描了,自己启动的这个项目也要被扫描到!!!!! com.line 可以扫描到所有组件
@SpringBootApplication
@ComponentScan(basePackages = {"com.line"})
// tkMapper dao 继承 Mapper
// mapperSacn 切换成 tk.mybatis.spring.annotation.MapperScan
@MapperScan(basePackages = "com.line.common.test.mybatis.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.line")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
