package com.bolin.service.impl;

import com.bolin.group2.dir1.cata1.service.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppServiceImplTest {
    @Autowired
    AppService appServiceImpl;
    @Test
    void queryWrapperSql() {
        appServiceImpl.queryWrapperSql();
    }
}