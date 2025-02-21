package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.demos.pojo.Question;
import com.bolin.group2.dir1.cata1.service.QuestionService;
import com.bolin.group2.dir1.cata1.service.ScoringResultService;
import com.bolin.mapper.QuestionMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-11-30 15:31:48
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {


    @Resource
    ScoringResultService scoringResultService;



}




