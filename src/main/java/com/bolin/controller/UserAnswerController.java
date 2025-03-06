package com.bolin.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bolin.group2.dir1.cata1.common.BaseResponse;
import com.bolin.group2.dir1.cata1.common.ErrorCode;
import com.bolin.group2.dir1.cata1.common.ResultUtils;
import com.bolin.group2.dir1.cata1.converter.UserAnswerServiceConverter;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.exception.ThrowUtils;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerAddRequest;
import com.bolin.group2.dir1.cata1.model.dto.userAnswer.UserAnswerQueryRequest;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 用户答案接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@RestController
@RequestMapping("/userAnswer")
@Slf4j
public class UserAnswerController {

    @Resource
    private UserAnswerService userAnswerService;



    // region 增删改查


    @PostMapping("/add")
    public BaseResponse<Long> addUserAnswer(@RequestBody UserAnswerAddRequest userAnswerAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userAnswerAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        UserAnswer userAnswer = new UserAnswer();
        BeanUtils.copyProperties(userAnswerAddRequest, userAnswer);
        List<String> choices = userAnswerAddRequest.getChoices();
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setOrderId(IdUtil.getSnowflakeNextId());

        // 写入数据库
        try {
            boolean result = userAnswerService.save(userAnswer);
            ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        } catch (DuplicateKeyException e) {
            // ignore error
        }
        // 返回新写入的数据 id
        long newUserAnswerId = userAnswer.getOrderId();

        return ResultUtils.success(newUserAnswerId);

    }

    @PostMapping("/getUserAnswerByAppId")
    public BaseResponse<List<UserAnswer>> getUserAnswerByAppId(@RequestBody Long appId) {
        return ResultUtils.success(userAnswerService.getUserAnswerByAppId(appId));

    }

    /**
     * 分页获取用户答案列表（仅管理员可用）
     *
     * @param userAnswerQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<UserAnswer>> listUserAnswerByPage(@RequestBody UserAnswerQueryRequest userAnswerQueryRequest) {
        long current = userAnswerQueryRequest.getCurrent();
        long size = userAnswerQueryRequest.getPageSize();
        // 查询数据库
        Page<UserAnswer> userAnswerPage = userAnswerService.page(new Page<>(current, size,false),
                userAnswerService.getQueryWrapper(userAnswerQueryRequest));
        return ResultUtils.success(userAnswerPage);
    }

    @PostMapping("/list/deepPage")
    public BaseResponse<Page<UserAnswer>> deepPage(@RequestBody UserAnswerQueryRequest userAnswerQueryRequest) {

        Page<UserAnswer> userAnswerPage = userAnswerService.deepPage(userAnswerQueryRequest);


        // 查询数据库
//        Page<UserAnswer> userAnswerPage = userAnswerService.page(new Page<>(current, size),
//                userAnswerService.getQueryWrapper(userAnswerQueryRequest));
        return ResultUtils.success(userAnswerPage);
//        return  null;
    }





    // endregion
    @GetMapping("/generate/id")
    public BaseResponse<Long> generateUserAnswerId() {
        return ResultUtils.success(IdUtil.getSnowflakeNextId());
    }


}
