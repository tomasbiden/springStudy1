package com.bolin.group1.MiDengXIng.XueHuaSuanfa;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;



@Component
public class Snowflake {

    public void try1(){

        for(int i=0;i<100;i++){
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            System.out.println(snowflakeNextId);
        }



    }
    public static void main(String[] args){
        for(int i=0;i<100;i++){
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            System.out.println(snowflakeNextId);
        }

    }
}
