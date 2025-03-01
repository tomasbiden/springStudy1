package com.bolin.group1.dir1.rocketMq.group1.dir1;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

//@Component
@RocketMQMessageListener(topic = "TestTopic",selectorExpression = "*",consumerGroup = "my-consumer_group_2")
public class Fifo1Consumer1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理消息的逻辑
        System.out.println("接收到消息: " +"my-consumer_group_2_1"+ message);
    }
}