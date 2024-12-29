package com.bolin.model.dto;

import lombok.Data;

@Data
public class DeviceSession {
    private String deviceId;
    private String deviceIp;
    private long lastAccessTime;

    // 构造方法，getter，setter等
}
