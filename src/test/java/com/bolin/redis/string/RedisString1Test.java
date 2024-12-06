package com.bolin.redis.string;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisString1Test {

    @Autowired
    RedisString1 redisString1;
    @Test
    void redisString1Method1() {
        for(int i=0;i<100;i++){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);

            redisString1.redisString1Method1(stringBuilder.toString());
        }

    }
}