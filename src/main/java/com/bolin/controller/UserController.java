package com.bolin.controller;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.bolin.group2.dir1.cata1.common.BaseResponse;
import com.bolin.group2.dir1.cata1.common.ErrorCode;
import com.bolin.group2.dir1.cata1.exception.BusinessException;
import com.bolin.group2.dir1.cata1.model.dto.user.UserLoginRequest;
import com.bolin.group2.dir1.cata1.model.param.AiQueryParam;
import com.bolin.group2.dir1.cata1.model.param.TestParam;
import com.bolin.group2.dir1.cata1.model.vo.LoginUserVO;
import com.bolin.group2.dir1.cata1.rpcService.TestService;
import com.bolin.group2.dir1.cata1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cn.hutool.extra.servlet.JakartaServletUtil.getClientIP;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TestService testService;


    @PostMapping("/test")
    public String test1(HttpServletRequest request, @RequestBody TestParam testParam) {
        // 获取客户端的 IP 地址
        String ipAddress = getClientIP(request);

        // 调用 service 层的方法
//        userService.test1();
        testService.test(request,testParam);
        // 返回结果并附带客户端 IP 地址
        return "Client IP: " + ipAddress + ", User List: " + userService.list().toString();
    }

    @RequestMapping("/login2")
    public String test2(HttpServletRequest request, @RequestBody TestParam testParam) {
        // 获取客户端的 IP 地址
        String ipAddress = getClientIP(request);

        // 调用 service 层的方法
        userService.test1();

        // 返回结果并附带客户端 IP 地址
        return "Client IP: " + ipAddress + ", User List: " + userService.list().toString();
    }

    @RequestMapping("/ai")
    public void ai(HttpServletRequest request, @RequestBody AiQueryParam aiQueryParam) throws NoApiKeyException, InputRequiredException {
        // 获取客户端的 IP 地址
        String ipAddress = getClientIP(request);

        // 调用 service 层的方法
        userService.ai(aiQueryParam);

        // 返回结果并附带客户端 IP 地址
//        return "Client IP: " + ipAddress + ", User List: " + userService.list().toString();
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BaseResponse<LoginUserVO> loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return loginUserVO;
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login3")
    public BaseResponse<LoginUserVO> userLogin3(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        BaseResponse<LoginUserVO> loginUserVO = userService.userLogin(userAccount, userPassword, request);

        return loginUserVO;
    }

    @RequestMapping("/getCount")
    public String getCount(HttpServletRequest request) {

        if(request.getRequestedSessionId()==null){
            return new String("你还未登陆");
        }else {
            return userService.getCount(request.getRequestedSessionId());
        }
    }

    @PostMapping("/remove")
    public void remove( HttpServletRequest request) {
        if(request.getRequestedSessionId()==null){
            return;
        }
        userService.redisRemove(request.getRequestedSessionId());

        return;
    }



}
