package com.bolin.demos.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppControllerTest {

    @Autowired
    AppController appController;
    @Test
    void hello() {
        appController.hello("hello");

    }
}