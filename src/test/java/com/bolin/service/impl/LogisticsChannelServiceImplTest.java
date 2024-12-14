package com.bolin.service.impl;

import com.bolin.converter.struct.LogisticsChannelConverter;
import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.LogisticsChannel;
import com.bolin.service.LogisticsChannelService;
import com.github.javafaker.Faker;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;



@SpringBootTest
class LogisticsChannelServiceImplTest {
    Faker faker = new Faker();
    @Autowired
    LogisticsChannelService logisticsChannelService;
    @Test
    void save() {


        // 创建模拟数据
        List<LogisticsChannelSaveParam> data = Arrays.asList(
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(faker.number().randomNumber(), faker.code().ean8(), faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2)),
                new LogisticsChannelSaveParam(7l, "4242", faker.company().name(), faker.name().username(), faker.code().isbn10(), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2), faker.random().nextInt(0, 2))
        );
        // 输出模拟数据
        for (LogisticsChannelSaveParam logisticsChannelSaveParam : data) {

            logisticsChannelService.save(logisticsChannelSaveParam);

        }
    }

    @Test
    void testSave() {
    }
}