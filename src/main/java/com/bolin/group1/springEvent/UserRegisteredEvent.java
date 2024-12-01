package com.bolin.group1.springEvent;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;





public class UserRegisteredEvent extends ApplicationEvent {

    public String ListenerName;
    public  Long appId;

    public UserRegisteredEvent( String ListenerName, Long appId) {
//        这里会进行一些初始化语句的呀
        super(ListenerName);
        this.ListenerName=ListenerName;
        this.appId = appId;
    }

    public String getListenerName(){
        return  this.ListenerName;
    }
    public Long getAppId(){
        return  this.appId;
    }



}