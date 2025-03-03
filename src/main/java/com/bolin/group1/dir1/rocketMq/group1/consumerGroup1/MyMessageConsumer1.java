package com.bolin.group1.dir1.rocketMq.group1.consumerGroup1;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "normalTopic1",selectorExpression = "messageTag",consumerGroup = "my-consumer_group_1_1",maxReconsumeTimes = 2)
public class MyMessageConsumer1 implements RocketMQListener<String> {
    int i=0;
    @Override
    public void onMessage(String message) {

        // 处理消息的逻辑
        i++;
        System.out.println("第"+i+"次");
        throw new RuntimeException("消费失败");
//        System.out.println("接收到消息: " + "my-consumer_group_1_1"+message);
    }
}
