package com.bolin.group1.rocketMq.group1;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;


@Slf4j
public class PushConsumerExample {


    private PushConsumerExample() {
    }

    public static void main(String[] args) throws ClientException, IOException, InterruptedException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        // 此处为示例，实际使用时请替换为真实的 Proxy 地址和端口

//        Thread.sleep(7000);
        String endpoints = "127.0.0.1:8080;127.0.0.1:8081";
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
            .setEndpoints(endpoints)
            .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // 为消费者指定所属的消费者分组，Group需要提前创建。
        String consumerGroup = "YourConsumerGroup";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "TestTopic";
        // 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
            .setClientConfiguration(clientConfiguration)
            // 设置消费者分组。
            .setConsumerGroup(consumerGroup)
            // 设置预绑定的订阅关系。
            .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
            // 设置消费监听器。
            .setMessageListener(messageView -> {
                // 处理消息并返回消费结果。

                log.info("Consume message successfully, messageId={}", messageView.getMessageId());
                ByteBuffer body = messageView.getBody();
                byte[] bytes = new byte[body.remaining()];
                body.get(bytes);
                String s = new String(bytes, Charset.defaultCharset());
                System.out.println(s);


                return ConsumeResult.SUCCESS;
            })
            .build();

        System.out.println("消费者结束了");
//        Thread.sleep(Long.MAX_VALUE);
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        // pushConsumer.close();
    }


}