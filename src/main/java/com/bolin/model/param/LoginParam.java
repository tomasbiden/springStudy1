package com.bolin.model.param;

import lombok.Data;

@Data
public class LoginParam {
    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;
}
