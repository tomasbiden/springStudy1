package com.bolin.group1.dir1.interceptor;


import com.bolin.group1.dir1.kfuka.cata1.Try1.Producer;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class AuthorityHandler implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthorityHandler.class);

    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.toString());
        log.info("对request进行前置处理啦,进行前置权限认证");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info(response.toString());
        log.info("mvc已经完成视图响应了啊");
    }



}
