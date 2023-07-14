package com.line.test.bloomfiltermanagetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {"com.line"})
@MapperScan(basePackages = "com.line.*.*.dao")
public class BloomfilterManageTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomfilterManageTestApplication.class, args);
    }

}
