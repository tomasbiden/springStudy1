package com.bolin.group2.dir1.cata1.config.dir1.group1.folder1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {

    private boolean enabled;
    private String defaultChannel;
    private List<Channel> channels;

    @Data
    public static class Channel {
        private String type;       // sms / email / wechat 等
        private String provider;   // aliyun / sendgrid / wecom 等

        // 通用字段
        private String apiKey;
        private String apiSecret;

        // Email 特有字段
        private String smtpHost;
        private Integer smtpPort;

        // WeChat 特有字段
        private String corpId;
        private String agentId;
    }
}
