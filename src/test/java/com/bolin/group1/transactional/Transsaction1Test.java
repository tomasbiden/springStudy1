package com.bolin.group1.transactional;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import static org.junit.jupiter.api.Assertions.*;



@Data
@SpringBootTest
class Transsaction1Test {

    @Autowired
    Transsaction1 transsaction1;
    @Test
    void transactionTest1() {

        transsaction1.transactionTest1();

    }
}