package com.bolin.group1.aop;

import com.bolin.group1.dir1.aop.In1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Proxy;

@SpringBootTest
class in1Inplement1Test {

    @Autowired
    In1 in1;

    @Test
    void test1() {
        // 检查 in1 是否是 JDK 动态代理
        if (in1 instanceof Proxy) {
            System.out.println("JDK 动态代理");
        } else if (in1.getClass().getName().contains("CGLIB")) {
            System.out.println("CGLIB 代理");
        } else {
            System.out.println("没有代理");
        }

        in1.test1();

    }
}