package com.sys;

import com.sys.core.configuration.ApplicationBootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * WebAppliaction springboot 项目启动入口
 *
 * @author zhong.h
 * @date 2019/10/31
 */
@SpringBootApplication
@EnableTransactionManagement // 事物注解
@ComponentScan(basePackages = {
        "com.sys.controller"
})
public class WebApplicationBootStrap extends ApplicationBootStrap {

    public static void main(String[] args) {
        new WebApplicationBootStrap().run(args);
    }
}
