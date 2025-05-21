package com.bolin.cn.hutool.StrUtil;

import cn.hutool.core.util.StrUtil;

public class StrUtilStudy1 {

    public static void main(String[] args){

        System.out.println(   StrUtil.format("该渠道{}（TO:{}）连续两小时同比昨天同一小时安装率下降幅度超过{}，请注意！", "13233", "toName",String.format("%.2f%%", -0.22 *(-1)* 100), "@All"));
    }
}
