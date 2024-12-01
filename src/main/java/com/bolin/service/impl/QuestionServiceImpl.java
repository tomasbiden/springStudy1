package com.bolin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.pojo.Question;
import com.bolin.service.QuestionService;
import com.bolin.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-11-30 15:31:48
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




