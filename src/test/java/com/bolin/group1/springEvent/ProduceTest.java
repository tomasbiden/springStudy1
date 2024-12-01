package com.bolin.group1.springEvent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProduceTest {
    @Autowired
    Produce produce;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Test
    void test1() throws InterruptedException {
        try {
            /*
            long appid = produce.test();
            for(int i=0;i<=10;i++){
                CompletableFuture<Long> ceshi = CompletableFuture.supplyAsync(() -> {
                    applicationEventPublisher.publishEvent(new UserRegisteredEvent("ceshi", appid));
                    return  appid;
                });
            }

             */
            long appid = produce.test();
//            applicationEventPublisher.publishEvent(new UserRegisteredEvent("ceshi", appid));



        }catch (Exception e){
            System.out.println("发生异常了啊，");
        }
        System.out.println("线程开始休息");
        Thread.sleep(100000);
    }
}