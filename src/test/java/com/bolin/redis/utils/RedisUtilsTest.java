package com.bolin.redis.utils;

import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.context.Theme;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilsTest {

    // 创建 VIP 用户的线程池，假设 VIP 用户优先，线程池大小为 10
    ExecutorService vipThreadPool = Executors.newFixedThreadPool(10);

    // 创建 普通 用户的线程池，线程池大小为 5
    ExecutorService regularThreadPool = Executors.newFixedThreadPool(5);
    @Test
    public  void test1() throws InterruptedException {
        RedissonClient client = RedisUtils.getClient();
        Config config = client.getConfig();
        RRateLimiter rateLimiter = client.getRateLimiter("test1");
        rateLimiter.setRate(RateType.OVERALL,5,30, RateIntervalUnit.SECONDS);

        AtomicInteger counter = new AtomicInteger(1);

        BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>();

        ThreadPoolExecutor vipThreadPool1 = new ThreadPoolExecutor(5, 10, 100L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        ThreadPoolExecutor regularThreadPool1 = new ThreadPoolExecutor(2, 4, 100L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());


        // 用一个列表来存储所有的 CompletableFuture
        CompletableFuture[] futures = new CompletableFuture[50];
        for (int i = 1; i <= 50; i++) {
            ExecutorService tmpThreadPool;
            if(i%2==1){
                 tmpThreadPool=vipThreadPool1;
            }else {
                tmpThreadPool=regularThreadPool1;
            }
            futures[i-1] = CompletableFuture.supplyAsync(() -> {

                int j = counter.getAndIncrement(); // 使用原子操作递增 i
                boolean b = rateLimiter.tryAcquire(5,TimeUnit.SECONDS);
                if (b) {
                    System.out.println(j + " 我成功进行了啊");
                } else {
                    System.out.println(j + " 我失败了压");
                }
                return  "hh";
            },tmpThreadPool);


        }
        CompletableFuture.allOf(futures).join();
        System.out.println("全部完成了压");






    }

}