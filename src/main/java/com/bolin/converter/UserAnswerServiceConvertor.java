package com.bolin.converter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bolin.demos.pojo.LogisticsChannel;
import com.bolin.demos.pojo.UserAnswer;

public class UserAnswerServiceConvertor {



    public static LambdaQueryWrapper<UserAnswer> toQueryByAppId(Long appId){
        LambdaQueryWrapper<UserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnswer::getAppId ,appId);
        return queryWrapper;

    }


}
