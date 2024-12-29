package com.bolin.controller;

import com.bolin.common.BaseResponse;
import com.bolin.common.ErrorCode;
import com.bolin.common.ResultUtils;
import com.bolin.exception.BusinessException;
import com.bolin.model.dto.user.UserLoginRequest;
import com.bolin.model.param.TestParam;
import com.bolin.model.vo.LoginUserVO;
import com.bolin.service.UserService;
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

    @RequestMapping("/test")
    public String test1(HttpServletRequest request, @RequestBody TestParam testParam) {
        // 获取客户端的 IP 地址
        String ipAddress = getClientIP(request);

        // 调用 service 层的方法
        userService.test1();

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
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }



}
