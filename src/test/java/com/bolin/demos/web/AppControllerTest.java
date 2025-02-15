package com.bolin.demos.web;

import com.bolin.group2.dir1.cata1.demos.web.AppController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppControllerTest {

    @Autowired
    AppController appController;
    @Test
    void hello() {
        appController.hello("hello");

    }
}