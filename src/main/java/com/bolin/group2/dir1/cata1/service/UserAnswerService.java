package com.bolin.group2.dir1.cata1.service;

import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service
* @createDate 2024-11-30 15:32:04
*/
public interface UserAnswerService extends IService<UserAnswer> {

    public List<UserAnswer> getUserAnswerByAppId(Long appId);


    public boolean saveUserAnswer(UserAnswer userAnswer);

}
