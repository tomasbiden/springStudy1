package com.bolin.group1.dir1.rocketMq.group1.consumerGroup1;

import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import com.bolin.group2.dir1.cata1.service.UserService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RocketMQMessageListener(topic = "normalTopic1",selectorExpression = "messageTag",consumerGroup = "normal-consumer_group_1_1",maxReconsumeTimes = 2)
public class MyMessageConsumer1 implements RocketMQListener<String> {
//   spiring 通过类型
    @Autowired
    UserAnswerService userAnswerService;
    int i=0;
    @Override
    public void onMessage(String message) {

        // 处理消息的逻辑
        i++;
//        System.out.println("第"+i+"次");
//        throw new RuntimeException("消费失败");
        System.out.println("接收到消息: " + "my-consumer_group_1_1"+message);
        UserAnswerQueryRequest userAnswerQueryRequest=new UserAnswerQueryRequest();
        userAnswerQueryRequest.setTenantId(8L);
        userAnswerQueryRequest.setAppId(2L);
        userAnswerQueryRequest.setCurrent(1);
        userAnswerQueryRequest.setPageSize(20);
        userAnswerService.deepPage(userAnswerQueryRequest);
    }
}
