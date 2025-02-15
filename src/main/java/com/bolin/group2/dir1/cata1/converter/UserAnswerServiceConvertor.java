package com.bolin.group2.dir1.cata1.converter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;

public class UserAnswerServiceConvertor {



    public static LambdaQueryWrapper<UserAnswer> toQueryByAppId(Long appId){
        LambdaQueryWrapper<UserAnswer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnswer::getAppId ,appId);
        return queryWrapper;

    }


}
