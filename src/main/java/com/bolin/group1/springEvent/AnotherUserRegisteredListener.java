package com.bolin.group1.springEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnotherUserRegisteredListener {
//,condition = "#event.username='ceshi'"
    @EventListener(classes = UserRegisteredEvent.class,condition ="event.ListenerName=='ceshi'" )
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        Long appId = event.getAppId();

        System.out.println("通过 @EventListener 监听，用户 " + appId + " 注册成功，记录注册日志...");
        // 这里可以添加记录日志等相关逻辑
    }
}