package com.bolin.group1.dir1.rocketMq.apisCnsumer.PushConsumer.fifoTopic.group1.dir1;

import com.bolin.group1.dir1.rocketMq.group1.PushConsumerExample;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.*;
import org.apache.rocketmq.shaded.org.slf4j.Logger;
import org.apache.rocketmq.shaded.org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Random;

public class ApisConsumerPushConsumerFIFOUtil1 {

//    private static final Logger logger = LoggerFactory.getLogger(PushConsumerExample.class);
    public static void fifoConsumer1() throws ClientException, InterruptedException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        // 此处为示例，实际使用时请替换为真实的 Proxy 地址和端口
        String endpoints = "127.0.0.1:8080;127.0.0.1:8081";
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // 为消费者指定所属的消费者分组，Group需要提前创建。
        String consumerGroup = "FifoTopic1ConsumerGroup1";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "FifoTopic1";
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
//                    logger.error("Consume message successfully, messageId={}", messageView.getMessageId());
                    ByteBuffer byteBuffer = messageView.getBody();
                byteBuffer.rewind();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes); // 将数据从 ByteBuffer 复制到字节数组
                    System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName()+":"+new String(bytes));
                    try {

                        Random random = new Random();

                        Thread.sleep( random.nextInt(1000,2500));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return ConsumeResult.SUCCESS;
                })
                .build();
        Thread.sleep(Long.MAX_VALUE);
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        // pushConsumer.close();

    }
    public static void main(String[] args) throws ClientException, InterruptedException {
        ApisConsumerPushConsumerFIFOUtil1.fifoConsumer1();

    }
}
