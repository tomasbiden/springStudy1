package com.bolin.group2.dir1.cata1.util.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;



public class UserData {

    @ExcelProperty("用户ID")
    @HeadStyle(fillForegroundColor = 1)
    private Long userId;

    @ExcelProperty("用户名")
    @HeadStyle(fillForegroundColor = 2)
    private String username;

    @ExcelProperty("年龄")
    @HeadStyle(fillForegroundColor = 4)
    private Integer age;

    @ExcelProperty("注册时间")
    @HeadStyle(fillForegroundColor = 13)
    private String registerTime;

    // 构造函数、Getter 和 Setter

    public UserData(Long userId, String username, Integer age, String registerTime) {
        this.userId = userId;
        this.username = username;
        this.age = age;
        this.registerTime = registerTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
