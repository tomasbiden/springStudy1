package com.bolin.redis.BuLongGuoLvQi;

import org.redisson.api.RedissonClient;
import org.redisson.api.RBloomFilter;
import org.redisson.config.Config;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class RedissonBloomFilterExample {

    @Autowired
    RedissonClient redissonClient;
    public static RedissonClient createRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");  // Redis服务器地址
        return Redisson.create(config);
    }

    public static void main(String[] args) {
        // 创建Redisson客户端
        RedissonClient redisson = createRedissonClient();

        // 创建布隆过滤器
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("myBloomFilter");
        
        // 初始化布隆过滤器，指定预期插入的元素数量和误判率
        bloomFilter.tryInit(1000000, 0.03);  // 预期插入100万条数据，误判率为 3%

        // 向布隆过滤器添加元素
        bloomFilter.add("item1");
        bloomFilter.add("item2");
        bloomFilter.add("item3");

        // 检查元素是否存在
        System.out.println("Contains 'item1': " + bloomFilter.contains("item1")); // true
        System.out.println("Contains 'item4': " + bloomFilter.contains("item4")); // false

        // 关闭 Redisson 客户端
        redisson.shutdown();
    }
}
