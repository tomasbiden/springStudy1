package com.bolin.group1.springEvent;

import com.bolin.mapper.AppMapper;
import com.bolin.demos.pojo.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class Produce {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    AppMapper appMapper;

    int h=3;
    @Transactional
   public  long test(){
        App app1 = appMapper.selectById(1);
        app1.setId((long)1511236569);
//        applicationEventPublisher.publishEvent(new UserRegisteredEvent("ceshi", app1.getId()));

            appMapper.insert(app1);
        applicationEventPublisher.publishEvent(new UserRegisteredEvent("ceshi", app1.getId()));

            return  app1.getId();
//            throw new RuntimeException("ceshi");
            /*
            App app = new App();
            app.setId((long)1);
            app.setAppname("ceshi");
            app.setUserid((long)1);
            appMapper.insert(app);

             */




   }
   public void publishAfterTransaction(){


   }

    public  void test2(){


        int m=5;
        throw  new RuntimeException();
    }
}
