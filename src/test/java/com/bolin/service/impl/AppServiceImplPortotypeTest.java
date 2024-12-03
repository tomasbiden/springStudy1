package com.bolin.service.impl;

import com.bolin.service.AppService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AppServiceImplPortotypeTest {

//    @Resource
    @Resource(name = "appServiceImpl")
    AppService appServiceImpl;

    @Resource(name = "appServiceImplPortotype")
    AppService appServiceImplPortotype;

    @Autowired
    private ApplicationContext context;
    @Test
    void test1() {

        // 从 ApplicationContext 获取相同 Bean 的两个实例
        Object appServiceImpl1 = context.getBean("appServiceImpl");
        Object appServiceImplPortotype1 = context.getBean("appServiceImplPortotype");



        // 验证它们是同一个实例
        assertSame(appServiceImpl, appServiceImpl1, "The beans should be the same instance (singleton).");


        assertNotSame(appServiceImplPortotype, appServiceImplPortotype1, "The beans should be the not same instance (singleton).");





    }
}