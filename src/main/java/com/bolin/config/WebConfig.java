package com.bolin.config;


import com.bolin.group1.dir1.interceptor.AuthorityHandler;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Resource
    AuthorityHandler authorityHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String testPath = "/user/test";
        registry.addInterceptor(authorityHandler).addPathPatterns(testPath);
    }

}
