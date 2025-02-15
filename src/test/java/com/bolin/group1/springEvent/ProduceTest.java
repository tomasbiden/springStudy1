package com.bolin.group1.springEvent;

import com.bolin.group1.dir1.springEvent.Produce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

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
//        阻塞package代码
//        Thread.sleep(100000);
    }
}