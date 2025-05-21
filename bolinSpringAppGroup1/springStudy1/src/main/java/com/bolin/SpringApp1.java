package com.bolin;

import cn.hutool.json.JSONUtil;
import com.bolin.group2.dir1.cata1.demos.web.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@MapperScan("com.bolin.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = false) // 使用 JDK 动态代理
@EnableFeignClients(basePackages = "com.bolin.group2.dir1.cata1.client")
@EnableDiscoveryClient
public class SpringApp1 {

    public static void main(String[] args) {
        JSONUtil jsonUtil = new JSONUtil();
        User user = new User();
        user.setAge(10);
        JSONUtil.toJsonStr(jsonUtil);

        SpringApplication.run(SpringApp1.class, args);
    }

}
