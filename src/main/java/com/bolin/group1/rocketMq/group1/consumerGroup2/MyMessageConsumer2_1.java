package com.bolin.group1.rocketMq.group1.consumerGroup2;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

//@Component
//@RocketMQMessageListener(topic = "TestTopic",selectorExpression = "*",consumerGroup = "my-consumer_group_2")
public class MyMessageConsumer2_1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理消息的逻辑
        System.out.println("接收到消息: " +"my-consumer_group_2_1"+ message);
    }
}
