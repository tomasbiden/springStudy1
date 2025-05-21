package com.bolin.group1.dir1.rocketMq.FIFO.group1.dir1;

import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import lombok.SneakyThrows;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
//@RocketMQMessageListener(topic = "FifoTopic1",selectorExpression = "tag1",consumerGroup = "fifoConsumerMutiThread",maxReconsumeTimes = 2,consumeMode= ConsumeMode.ORDERLY)
public class FifoConsumerWithOrderConsumeSet implements RocketMQListener<String> {
//   spiring 通过类型
    @Autowired
    UserAnswerService userAnswerService;
    int i=0;
    @SneakyThrows
    @Override
    public void onMessage(String message) {

        // 处理消息的逻辑
        i++;
//        System.out.println("第"+i+"次");
//        throw new RuntimeException("消费失败");
        System.out.println("接收到消息: " +   "结果:"+Thread.currentThread().getId()+Thread.currentThread().getName()+" "+message);
        Random random = new Random();
        Thread.sleep(random.nextInt(500,1000));

//        UserAnswerQueryRequest userAnswerQueryRequest=new UserAnswerQueryRequest();
//        userAnswerQueryRequest.setTenantId(8L);
//        userAnswerQueryRequest.setAppId(2L);
//        userAnswerQueryRequest.setCurrent(100000);
//        userAnswerQueryRequest.setPageSize(40);
//        userAnswerService.deepPage(userAnswerQueryRequest);
    }
}
