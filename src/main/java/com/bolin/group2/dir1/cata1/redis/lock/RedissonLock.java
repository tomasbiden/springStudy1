package com.bolin.group2.dir1.cata1.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLock {

    @Autowired
    RedissonClient redissonClient;

    public void redissonLockTest1() throws InterruptedException {
        RLock lock = redissonClient.getLock("ceshiLock");

        // 获取一个分布式锁对象
//        RLock lock = redisson.getLock("myLock");

        try {
            // 尝试加锁，最多等待10秒，锁定时间为5秒
            boolean isLocked = lock.tryLock(3, 15, TimeUnit.SECONDS);
            if (isLocked) {
                System.out.println("成功获取锁！");

                // 这里可以执行需要同步的代码
                // 模拟执行一些任务
                Thread.sleep(3000); // 模拟任务执行时间

            } else {
                System.out.println("无法获取锁！");
            }
        } finally {
            // 在 finally 中释放锁，确保锁被释放
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("锁已释放！");
            }
            // 关闭 Redisson 客户端连接
//            redissonClient.shutdown();
        }


    }
}
