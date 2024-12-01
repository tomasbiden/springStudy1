package com.bolin.group1.MiDengXIng.XueHuaSuanfa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class XueHuaSuanfa1Test {

//    添加个springbootTest就没有问题了啊
    @Autowired
Snowflake xueHuaSuanfa1;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void try1() {

        xueHuaSuanfa1.try1();

    }
}