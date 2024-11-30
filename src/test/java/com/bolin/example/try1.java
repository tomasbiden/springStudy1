package com.bolin.example;

import com.bolin.demos.web.AppController;
import com.bolin.mapper.AppMapper;
import com.bolin.pojo.App;
import com.bolin.service.AppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class try1 {
    @Autowired
    private  AppMapper appMapper;

    @Autowired
    private AppService appService;

    @Autowired
    private AppController appController;
    @Test
    public void  test1(){
        long id=1;
        App app = appMapper.selectById(id);
        try {
            appService.transacionalTest();
        }catch (Exception e){
            System.out.println(e);
        }
//        assertEquals("本地测试自定义本地MBTI性格测试", app.getAppname());

    }

    @Test
    public void  test2(){

        String dsfs = appController.hello("dsfs");
        System.out.println(dsfs);

    }

}
