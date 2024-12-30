package com.bolin.rpcService;

import com.bolin.client.AFeignClient;
import com.bolin.common.BaseResponse;
import com.bolin.model.dto.app.AppQueryRequest;
import com.bolin.model.vo.AppVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    AFeignClient aFeignClient;

    public String test(HttpServletRequest httpServletRequest){
//        String dataFromA = aFeignClient.getDataFromA();
        long x=1;
        AppVO appVOById = aFeignClient.getAppVOById(x);
        AppQueryRequest appQueryRequest = new AppQueryRequest();
        appQueryRequest.setAppDesc("testDesc");
        appQueryRequest.setAppName("appName");
        AppVO appVO = aFeignClient.postTest(appQueryRequest);

        return  new String("ceshi");
    }

}
