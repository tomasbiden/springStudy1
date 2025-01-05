package com.bolin.service;

import com.bolin.demos.vo.AppWithUserAnswerVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppServiceTest {

    @Autowired
    AppService appServiceImpl;

    @Test
    void getAppWithUserAnswerByAppId() {

        AppWithUserAnswerVo appWithUserAnswerByAppId = appServiceImpl.getAppWithUserAnswerByAppId(1l);
        int h=1;
    }
}