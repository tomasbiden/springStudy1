package com.bolin.group2.dir1.cata1.model.dto;

import lombok.Data;

@Data
public class DeviceSession {
    private String deviceId;
    private String deviceIp;
    private long lastAccessTime;

    // 构造方法，getter，setter等
}
