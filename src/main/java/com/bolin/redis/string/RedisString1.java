package com.bolin.redis.string;

import com.bolin.redis.config.RedissonConfig;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisString1 {

    @Autowired
    RedissonClient redisson;

    public void redisString1Method1(String s){

        // 获取 Redis 中的 String 对象（RBucket）
        RBucket<String> bucket = redisson.getBucket("myStringKey");

        // 将值存入 Redis
        bucket.set(s);

        // 从 Redis 获取值
        String value = bucket.get();
        System.out.println("Stored value: " + value);

        // 关闭 Redisson 客户端  暂时不要关闭吧
//        redisson.shutdown();
    }





}
