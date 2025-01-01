package com.bolin.redis.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Detail implements Serializable {
    String userName;
    Long count;
    Long createDate;
    Long expireDate;
    Long lastAccessDate;

}
