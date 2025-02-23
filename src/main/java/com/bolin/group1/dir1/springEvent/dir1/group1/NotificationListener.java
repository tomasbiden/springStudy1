package com.bolin.group1.dir1.springEvent.dir1.group1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
    public  class NotificationListener {


        // 只监听OrderCreateEvent类型（两种等效写法）
//     ,condition = "#event.orderId != null "   && event.orderId == '202308150001'
//        @EventListener(classes = OrderCreateEvent.class)

//          @EventListener(value = OrderCreateEvent.class)
//        @EventListener(condition = "#root.args[0].orderId =='202308150001'")
//        去掉#就一切正常了
        @EventListener(condition = "#root.event().orderId == '202308150001'")

        public void sendNotifications(EventDemoApplication.OrderCreateEvent event) {
            System.out.println("开始通知了啊");
        }
    }