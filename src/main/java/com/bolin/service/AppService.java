package com.bolin.service;

import com.bolin.pojo.App;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【app(应用)】的数据库操作Service
* @createDate 2024-11-30 14:26:59
*/
public interface AppService extends IService<App> {
    public  void test1();

    public void transacionalTest();

    public  void appJoinTest();

    public void queryWrapperSql();

}
