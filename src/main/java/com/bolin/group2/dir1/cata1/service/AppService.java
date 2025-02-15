package com.bolin.group2.dir1.cata1.service;

import com.bolin.group2.dir1.cata1.demos.pojo.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bolin.group2.dir1.cata1.demos.vo.AppWithUserAnswerVo;

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

    public AppWithUserAnswerVo getAppWithUserAnswerByAppId(Long AppId);

}
