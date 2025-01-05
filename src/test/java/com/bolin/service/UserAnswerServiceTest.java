package com.bolin.service;

import com.bolin.demos.pojo.UserAnswer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAnswerServiceTest {
    @Autowired
    UserAnswerService userAnswerService;

    @Test
    public void test1(){
        Long appId=1l;
        List<UserAnswer> userAnswerByAppId = userAnswerService.getUserAnswerByAppId(appId);

        int h=1;
    }


    @Test
    void saveUserAnswer() {
        // 创建一个 Faker 实例，用于生成假数据
        Faker faker = new Faker();
        Random random = new Random();

        // 模拟 100 次保存用户答案
        for (int i = 0; i < 100; i++) {
            // 创建一个 UserAnswer 对象
            UserAnswer userAnswer = new UserAnswer();

            // 使用 Faker 生成随机数据

            userAnswer.setAppId(1l);  // 随机生成 appId
            userAnswer.setAppType(random.nextInt(2));  // 随机选择 0 或 1
            userAnswer.setScoringStrategy(random.nextInt(2));  // 随机选择 0 或 1
            userAnswer.setChoices("[\"Choice 1\", \"Choice 2\", \"Choice 3\"]");  // 模拟 JSON 数组（可根据需求扩展）
            userAnswer.setResultId(faker.random().nextLong());  // 随机生成 resultId
            userAnswer.setResultName(faker.job().title());  // 随机生成 resultName（如 "物流师"）
            userAnswer.setResultDesc(faker.lorem().sentence());  // 随机生成 resultDesc
            userAnswer.setResultPicture(faker.internet().image());  // 随机生成 resultPicture
            userAnswer.setResultScore(faker.number().numberBetween(0, 100));  // 随机生成 resultScore
            userAnswer.setUserId(faker.random().nextLong());  // 随机生成 userId

            // 调用 saveUserAnswer 方法
            userAnswerService.saveUserAnswer(userAnswer);
        }

    }
}