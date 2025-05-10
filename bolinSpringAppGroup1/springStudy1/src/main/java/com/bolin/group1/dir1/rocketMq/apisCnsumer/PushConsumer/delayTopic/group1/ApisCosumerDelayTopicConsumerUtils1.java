package com.bolin.group1.dir1.rocketMq.apisCnsumer.PushConsumer.delayTopic.group1;

import com.bolin.group1.dir1.rocketMq.apisCnsumer.PushConsumer.fifoTopic.group1.dir1.ApisConsumerPushConsumerFIFOUtil1;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;

public class ApisCosumerDelayTopicConsumerUtils1 {
    /*
    public static void delayConsumer1() throws ClientException, InterruptedException {
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
        String consumerGroup = "DelayTopic1ConsumerGroup1";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "DelayTopic1";
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
//                    ByteBuffer byteBuffer = messageView.getBody();
//                    byteBuffer.rewind();
//                    byte[] bytes = new byte[byteBuffer.remaining()];
//                    byteBuffer.get(bytes); // 将数据从 ByteBuffer 复制到字节数组
//                    System.out.println(messageView.getDeliveryTimestamp()+"当前时间"+System.currentTimeMillis());

                    return ConsumeResult.SUCCESS;
                })
                .build();
        Thread.sleep(Long.MAX_VALUE);
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        // pushConsumer.close();

    }

     */

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 将时间戳转换为可读的日期时间字符串
     */
    public static String formatTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        ).format(FORMATTER);
    }

    public static void delayConsumer2() throws ClientException, InterruptedException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8080;xxx:8081
        // 此处为示例，实际使用时请替换为真实的 Proxy 地址和端口
        String endpoints = "127.0.0.1:8080;127.0.0.1:8081";
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "tag1";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // 为消费者指定所属的消费者分组，Group需要提前创建。
        String consumerGroup = "DelayTopic1ConsumerGroup1";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "dev%DelayTopic1";
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
//                    System.out.println(formatTimestamp(messageView.getDeliveryTimestamp().get())+" " +formatTimestamp(System.currentTimeMillis()));
                    long bornTimestamp = messageView.getBornTimestamp();
                    long deliveryTimestamp = messageView.getDeliveryTimestamp().orElse(0L);

                    System.out.printf("Born: %s | Delivery: %s%n",
                            formatTimestamp(bornTimestamp),
//                            formatTimestamp(storeTimestamp),
                            formatTimestamp(deliveryTimestamp));
                    return ConsumeResult.SUCCESS;
                })
                .build();
        Thread.sleep(Long.MAX_VALUE);
        // 如果不需要再使用 PushConsumer，可关闭该实例。
        // pushConsumer.close();

    }
    public static void main(String[] args) throws ClientException, InterruptedException {
        ApisCosumerDelayTopicConsumerUtils1.delayConsumer2();

    }
}
