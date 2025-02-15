package com.bolin.mapper;

import com.bolin.group2.dir1.cata1.demos.vo.AppVo1;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AppMapperTest {
    @Resource
    AppMapper appMapper;
    @Test
    void selectUserWithOrders() {
//        List<AppVo1> appVo1s = appMapper.selectTest1(1l);
        List<AppVo1> appVo1s1 = appMapper.selectTestByXml(null);


        int h=1;
    }
}