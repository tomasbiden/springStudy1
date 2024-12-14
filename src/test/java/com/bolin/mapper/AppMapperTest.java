package com.bolin.mapper;

import com.bolin.demos.vo.AppVo1;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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