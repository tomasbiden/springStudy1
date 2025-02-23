package com.bolin.group1.dir1.springEvent.dir1.group1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bolin.Application;
import com.bolin.controller.UserAnswerController;
import com.bolin.group2.dir1.cata1.common.BaseResponse;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.demos.web.AppController;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MapperImportTest1 {



    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        /*
        EventDemoApplication.OrderService service = context.getBean(EventDemoApplication.OrderService.class);
        UserAnswerController userAnswerController = context.getBean(UserAnswerController.class);
        UserAnswerQueryRequest userAnswerQueryRequest = new UserAnswerQueryRequest();
        userAnswerQueryRequest.setCurrent(1);
        userAnswerQueryRequest.setPageSize(30);
        userAnswerQueryRequest.setAppId(6L);

        BaseResponse<Page<UserAnswer>> pageBaseResponse = userAnswerController.listUserAnswerByPage(userAnswerQueryRequest);

        // 创建并发测试线程池（与事件处理线程池分开）
        ExecutorService concurrentExecutor = Executors.newFixedThreadPool(10);

        // 模拟20个并发请求
        int requestCount = 1;
        CountDownLatch latch = new CountDownLatch(requestCount);

        System.out.println("===== 开始并发测试 =====");

        for (int i = 0; i <requestCount; i++) {
            final String orderId = "20230815" + String.format("%04d", i+1);
            concurrentExecutor.execute(() -> {
                try {
                    service.createOrder(orderId);
                } catch (Exception e){
                    System.out.println(e);
                }
                finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有请求完成
        latch.await(30, TimeUnit.SECONDS);

        // 关闭资源
        concurrentExecutor.shutdown();
        context.close();

        System.out.println("===== 并发测试完成 =====");




    }

}
