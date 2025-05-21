package com.bolin.service;

import com.bolin.group2.dir1.cata1.demos.vo.AppWithUserAnswerVo;
import com.bolin.group2.dir1.cata1.service.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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