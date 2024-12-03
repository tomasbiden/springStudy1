package com.bolin.redis.lock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedissonLockTest {

    @Autowired
    RedissonLock redissonLock;
    @Test
    void redissonLockTest1() throws InterruptedException {

        redissonLock.redissonLockTest1();

    }
}