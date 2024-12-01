package com.bolin.demos.vo;

import lombok.Data;

import java.util.List;

@Data
public class AppVo1 {
    long id;

    /**
     * 应用名
     */
    private String appName;

    private List<String> questionContent;

}
