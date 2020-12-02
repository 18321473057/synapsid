package com.line.common.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import tk.mybatis.spring.annotation.MapperScan;

//scanBasePackages="com.line" 自动装配代码 只需要被包扫描到
//他妈的 设置之后就没有默认的扫描了,自己启动的这个项目也要被扫描到!!!!! com.line 可以扫描到所有组件
@SpringBootApplication
@ComponentScan(
        basePackages = {"com.line"},
        excludeFilters =
                {
//                        排除各种依赖缓存的功能 , 每次启动都需要开启redis 很烦
                        @ComponentScan.Filter(type = FilterType.REGEX,
                                pattern = {})
                })
// tkMapper dao 继承 Mapper
// mapperSacn 切换成 tk.mybatis.spring.annotation.MapperScan
@MapperScan(basePackages = "com.line.common.test.mybatis.dao")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
