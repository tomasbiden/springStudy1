package com.bolin.group2.dir1.cata1.redis.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Detail implements Serializable {
    String userName;
    String userAccount;
    Long count;
    Long createDate;
    Long expireDate;
    Long lastAccessDate;
    Boolean needCheck;

}
