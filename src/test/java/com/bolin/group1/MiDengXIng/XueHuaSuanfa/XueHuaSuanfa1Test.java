package com.bolin.group1.MiDengXIng.XueHuaSuanfa;

import com.bolin.service.ScoringResultService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class XueHuaSuanfa1Test {

//    添加个springbootTest就没有问题了啊
    @Autowired
    XueHuaSuanfa1 xueHuaSuanfa1;


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