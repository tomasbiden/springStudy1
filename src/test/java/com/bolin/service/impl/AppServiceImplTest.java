package com.bolin.service.impl;

import com.bolin.service.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppServiceImplTest {
    @Autowired
    AppService appServiceImpl;
    @Test
    void queryWrapperSql() {
        appServiceImpl.queryWrapperSql();
    }
}