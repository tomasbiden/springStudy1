package com.bolin.rpcService;

import com.bolin.client.AFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    AFeignClient aFeignClient;

    public String test(){
        String dataFromA = aFeignClient.getDataFromA();
        return  dataFromA;
    }

}
