package com.bolin.group1.MiDengXIng.XueHuaSuanfa;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class XueHuaSuanfa1 {

    public void try1(){
        for(int i=0;i<100;i++){
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            System.out.println(snowflakeNextId);
        }




    }
    public static void main(String[] args){

    }
}
