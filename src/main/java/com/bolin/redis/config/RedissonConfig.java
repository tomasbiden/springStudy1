package com.bolin.redis.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig {


    private String host;

    private Integer port;

    private Integer database;

    private String password;

    private String username;



    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                .setPassword(password);
        // 设置编码格式为 UTF-8
        config.setCodec(new org.redisson.client.codec.StringCodec());
        return Redisson.create(config);
    }
}