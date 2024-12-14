package com.bolin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.demos.pojo.UserAnswer;
import com.bolin.service.UserAnswerService;
import com.bolin.mapper.UserAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service实现
* @createDate 2024-11-30 15:32:04
*/
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer>
    implements UserAnswerService{

}




