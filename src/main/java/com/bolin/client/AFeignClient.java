package com.bolin.client;

import com.bolin.common.BaseResponse;
import com.bolin.model.dto.app.AppQueryRequest;
import com.bolin.model.vo.AppVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(url = "http://localhost:8081/api/app",name = "feignCustomerService")
public interface AFeignClient {
    @GetMapping("/test")
//    @Headers(value = "Content-Type: application/json")
    public String getDataFromA();

//    注意参数 注意request
    @GetMapping ("/get/vo")
//    @Headers(value = "Content-Type: application/json")
    public AppVO getAppVOById(@RequestParam("id")long id);

    @PostMapping("/postTest")
    public AppVO postTest(@RequestBody AppQueryRequest appQueryRequest);

    @GetMapping("/get/vo2")
    public String getAppVOById2(@RequestParam("id")long id);

}
