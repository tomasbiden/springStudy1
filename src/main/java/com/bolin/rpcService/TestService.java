package com.bolin.rpcService;


import com.bolin.client.AFeignClient;
import com.bolin.common.BaseResponse;
import com.bolin.model.dto.app.AppQueryRequest;
import com.bolin.model.vo.AppVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TestService {
    @Autowired
    AFeignClient aFeignClient;

    @Autowired
    ObjectMapper objectMapper;

    public String test(HttpServletRequest httpServletRequest){
//        String dataFromA = aFeignClient.getDataFromA();
        long x=1;
//        AppVO appVOById = aFeignClient.getAppVOById(x);
        AppQueryRequest appQueryRequest = new AppQueryRequest();
        appQueryRequest.setAppDesc("testDesc");
        appQueryRequest.setAppName("appName");
//        AppVO appVO = aFeignClient.postTest(appQueryRequest);
        long id=1;
        String dataWithCode = aFeignClient.getAppVOById2(id);
        try {
            // 3. 使用 ObjectMapper 将 JSON 字符串转换为 Java 对象
            BaseResponse<AppVO> baseResponse = objectMapper.readValue(dataWithCode, new TypeReference<BaseResponse<AppVO>>() {});

            // 4. 处理转换后的对象
            AppVO appVO = baseResponse.getData();
            int code = baseResponse.getCode();
            String message = baseResponse.getMessage();
            // 根据需要处理 appVO、code、message
        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理
        }


        return  new String("ceshi");
    }

}
