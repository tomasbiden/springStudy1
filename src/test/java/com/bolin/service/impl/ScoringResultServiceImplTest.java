package com.bolin.service.impl;

import com.bolin.group2.dir1.cata1.service.ScoringResultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScoringResultServiceImplTest {

    @Autowired
    private ScoringResultService scoringResultService;



    @Test
    public  void test1(){
        scoringResultService.test1();
    }

}