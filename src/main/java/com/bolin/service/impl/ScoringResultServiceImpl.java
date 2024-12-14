package com.bolin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.demos.pojo.ScoringResult;
import com.bolin.service.ScoringResultService;
import com.bolin.mapper.ScoringResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【scoring_result(评分结果)】的数据库操作Service实现
* @createDate 2024-11-30 15:33:35
*/
@Service
public class ScoringResultServiceImpl extends ServiceImpl<ScoringResultMapper, ScoringResult>
    implements ScoringResultService{
    @Autowired
    ScoringResultMapper scoringResultMapper;
    @Override
    public void test1() {
        long id=2;
        ScoringResult scoringResult = scoringResultMapper.selectById(id);
        System.out.println(scoringResult);


    }
}




