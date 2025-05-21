package com.bolin.group1.dir1.aop;

import org.springframework.stereotype.Component;

@Component
public class In1Inplement1 implements In1 {



    @annotion1(value = "yali")
    @Override
    public void test1() {
        System.out.println("ceshi");
    }
}
