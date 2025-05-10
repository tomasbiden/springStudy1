package com.bolin.group2.dir1.cata1.model.dto.statistic;

import lombok.Data;

/**
 * App 用户提交答案书统计
 */
@Data
public class AppAnswerCountDTO {

    private Long appId;
    private  String appName;

    /**
     * 用户提交答案数
     */
    private Long answerCount;
}