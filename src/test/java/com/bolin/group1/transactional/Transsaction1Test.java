package com.bolin.group1.transactional;

import com.bolin.group1.dir1.transactional.Transsaction1;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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