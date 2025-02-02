package com.bolin.client;

import com.bolin.common.BaseResponse;
import com.bolin.model.dto.app.AppQueryRequest;
import com.bolin.model.vo.AppVO;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


//@FeignClient(url = "http://localhost:8081/api/app",name = "yudada-backend")
@FeignClient(name = "nova-ai-score")
//@EnableFeignClients
public interface AFeignClient {
    @GetMapping("/test")
//    @Headers(value = "Content-Type: application/json")
    public String getDataFromA();

//    注意参数 注意request
    @GetMapping ("/api/get/vo")
//    @Headers(value = "Content-Type: application/json")
    public AppVO getAppVOById(@RequestParam("id")long id);

    @PostMapping("/postTest")
    public AppVO postTest(@RequestBody AppQueryRequest appQueryRequest);

    @GetMapping("/api/app/get/vo2")
    public String getAppVOById2(@RequestParam("id")long id);



}
