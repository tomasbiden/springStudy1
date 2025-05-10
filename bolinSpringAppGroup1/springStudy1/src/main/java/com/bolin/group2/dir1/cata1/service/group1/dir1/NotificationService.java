package com.bolin.group2.dir1.cata1.service.group1.dir1;

import cn.hutool.core.map.CaseInsensitiveLinkedMap;
import com.bolin.group2.dir1.cata1.config.dir1.group1.folder1.NotificationProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class NotificationService implements InitializingBean {

    private final NotificationProperties notificationProperties;

    public final Map<String,NotificationProperties.Channel> channelTypeToChannleMap=new CaseInsensitiveLinkedMap<>();

    public NotificationService(NotificationProperties notificationProperties) {
        this.notificationProperties = notificationProperties;
    }

    public void listAllChannels() {
        if (!notificationProperties.isEnabled()) {
            System.out.println("通知功能未启用");
            return;
        }

        for (NotificationProperties.Channel channel : notificationProperties.getChannels()) {
            System.out.println("渠道类型：" + channel.getType());
            System.out.println("提供商：" + channel.getProvider());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notificationProperties.getChannels().forEach(channel -> {
            channelTypeToChannleMap.put(channel.getType(),channel);
        });
    }


   @PostConstruct
    public void postConstructTest(){
       notificationProperties.getChannels().forEach(channel -> {
           channelTypeToChannleMap.put(channel.getType(),channel);
       });

   }
}
