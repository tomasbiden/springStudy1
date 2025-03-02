package com.bolin.group2.dir1.cata1.converter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;

public class UserAnswerServiceConverter {



    public static LambdaQueryWrapper<UserAnswer> toQueryByAppId(Long appId){
        LambdaQueryWrapper<UserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnswer::getAppId ,appId);
        return queryWrapper;

    }

    public  static LambdaQueryWrapper<UserAnswer> getDeepPagePageQuery(UserAnswerQueryRequest userAnswerQueryRequest){
        int current = userAnswerQueryRequest.getCurrent();
        int pageSize = userAnswerQueryRequest.getPageSize();
        int skip=(current-1)*pageSize;

        LambdaQueryWrapper<UserAnswer> userAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userAnswerLambdaQueryWrapper.exists("select * from user_answer where id >"+skip+"limit"+pageSize+"as join_table"+"where user_answer.id=join_table.id");
        return userAnswerLambdaQueryWrapper;

    }


}
