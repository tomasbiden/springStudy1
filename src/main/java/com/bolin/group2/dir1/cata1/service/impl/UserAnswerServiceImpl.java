package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.converter.UserAnswerServiceConverter;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import com.bolin.mapper.UserAnswerMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service实现
* @createDate 2024-11-30 15:32:04
*/
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer>
    implements UserAnswerService {

    @Override
    public List<UserAnswer> getUserAnswerByAppId(Long appId) {
        LambdaQueryWrapper<UserAnswer> queryByAppId = UserAnswerServiceConverter.toQueryByAppId(appId);
        List<UserAnswer> userAnswers = getBaseMapper().selectList(queryByAppId);

        return userAnswers;

    }

    public boolean saveUserAnswer(UserAnswer userAnswer){
        if(userAnswer!=null){
            boolean l = this.save(userAnswer);
            return l;
        }else {
            return false;
        }



    }

    /**
     * 获取查询条件
     *
     * @param userAnswerQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<UserAnswer> getQueryWrapper(UserAnswerQueryRequest userAnswerQueryRequest) {
        QueryWrapper<UserAnswer> queryWrapper = new QueryWrapper<>();
        if (userAnswerQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = userAnswerQueryRequest.getId();
        Long appId = userAnswerQueryRequest.getAppId();
        Integer appType = userAnswerQueryRequest.getAppType();
        Integer scoringStrategy = userAnswerQueryRequest.getScoringStrategy();
        String choices = userAnswerQueryRequest.getChoices();
        Long resultId = userAnswerQueryRequest.getResultId();
        String resultName = userAnswerQueryRequest.getResultName();
        String resultDesc = userAnswerQueryRequest.getResultDesc();
        String resultPicture = userAnswerQueryRequest.getResultPicture();
        Integer resultScore = userAnswerQueryRequest.getResultScore();
        Long userId = userAnswerQueryRequest.getUserId();
        Long notId = userAnswerQueryRequest.getNotId();
        String searchText = userAnswerQueryRequest.getSearchText();
        String sortField = userAnswerQueryRequest.getSortField();
        String sortOrder = userAnswerQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("resultName", searchText).or().like("resultDesc", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(choices), "choices", choices);
        queryWrapper.like(StringUtils.isNotBlank(resultName), "resultName", resultName);
        queryWrapper.like(StringUtils.isNotBlank(resultDesc), "resultDesc", resultDesc);
        queryWrapper.like(StringUtils.isNotBlank(resultPicture), "resultPicture", resultPicture);
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"user_id", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userAnswerQueryRequest.getTenantId()),"tenant_id", userAnswerQueryRequest.getTenantId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(resultId), "result_id", resultId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(appId),"app_id", appId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(appType), "app_type", appType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(resultScore), "result_score", resultScore);
        queryWrapper.eq(ObjectUtils.isNotEmpty(scoringStrategy), "scoring_strategy", scoringStrategy);
        // 排序规则
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
//                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
        queryWrapper.orderBy(true,false,"create_time");
        return queryWrapper;
    }

    @Override
    public Page<UserAnswer> deepPage(UserAnswerQueryRequest userAnswerQueryRequest) {
        long current = userAnswerQueryRequest.getCurrent();
        long size = userAnswerQueryRequest.getPageSize();
        LambdaQueryWrapper<UserAnswer> deepPagePageIdQuery = UserAnswerServiceConverter.getDeepPagePageIdQuery(userAnswerQueryRequest);
        UserAnswer skipIdUserAnswer = getBaseMapper().selectOne(deepPagePageIdQuery);
        LambdaQueryWrapper<UserAnswer> deepPagePageQuery = UserAnswerServiceConverter.getDeepPagePageQuery(userAnswerQueryRequest,skipIdUserAnswer.getId());
        List<UserAnswer> userAnswersRecord = getBaseMapper().selectList(deepPagePageQuery);
        Page<UserAnswer> userAnswerPage = new Page<>();
        userAnswerPage.setRecords(userAnswersRecord);

        return userAnswerPage;
    }
}




