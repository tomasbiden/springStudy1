package com.bolin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.bolin.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = false) // 使用 JDK 动态代理
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
