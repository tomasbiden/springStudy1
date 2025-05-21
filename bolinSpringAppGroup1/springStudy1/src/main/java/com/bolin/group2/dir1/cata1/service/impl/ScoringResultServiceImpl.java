package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.demos.pojo.ScoringResult;
import com.bolin.group2.dir1.cata1.service.QuestionService;
import com.bolin.group2.dir1.cata1.service.ScoringResultService;
import com.bolin.mapper.ScoringResultMapper;
import groovy.lang.Lazy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【scoring_result(评分结果)】的数据库操作Service实现
* @createDate 2024-11-30 15:33:35
*/
@Service
public class ScoringResultServiceImpl extends ServiceImpl<ScoringResultMapper, ScoringResult>
    implements ScoringResultService {
    @Autowired
    ScoringResultMapper scoringResultMapper;

    @Resource
    QuestionService questionService;


    @Override
    public void test1() {
        long id=2;
        ScoringResult scoringResult = scoringResultMapper.selectById(id);
        System.out.println(scoringResult);


    }
}




