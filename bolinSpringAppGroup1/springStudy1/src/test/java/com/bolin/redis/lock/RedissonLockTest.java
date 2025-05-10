package com.bolin.redis.lock;

import com.bolin.group2.dir1.cata1.redis.lock.RedissonLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedissonLockTest {

    @Autowired
    RedissonLock redissonLock;
    @Test
    void redissonLockTest1() throws InterruptedException {

        redissonLock.redissonLockTest1();

    }
}