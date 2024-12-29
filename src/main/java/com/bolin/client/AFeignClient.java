package com.bolin.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(url = "http://localhost:8081/api/app",name = "feignCustomerService")
public interface AFeignClient {
    @GetMapping("/test")
//    @Headers(value = "Content-Type: application/json")
    public String getDataFromA();
}
