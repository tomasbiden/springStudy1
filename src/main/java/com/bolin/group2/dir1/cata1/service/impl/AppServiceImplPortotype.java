package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.demos.vo.AppWithUserAnswerVo;
import com.bolin.mapper.AppMapper;
import com.bolin.group2.dir1.cata1.demos.pojo.App;
import com.bolin.group2.dir1.cata1.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author Administrator
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2024-11-30 14:26:59
*/
@Service
@Scope(value = "prototype")
public class AppServiceImplPortotype extends ServiceImpl<AppMapper, App>
    implements AppService{
    @Autowired
    private  AppMapper appMapper;



    public void  test1(){
        int h=1;
        App app = appMapper.selectById(1);

        return;

    }

    @Override
    @Transactional
    public void transacionalTest() {

        App app = appMapper.selectById((long) 1);
        app.setId((long)5678);

        appMapper.insert(app);
        System.out.println("插入成功");
//        appMapper.deleteById((long)567);
        System.out.println("回退测试");
        throw new RuntimeException("模拟事务回滚");


    }

    @Override
    public void appJoinTest() {
        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();


    }

    @Override
    public void queryWrapperSql() {

    }

    @Override
    public AppWithUserAnswerVo getAppWithUserAnswerByAppId(Long AppId) {
        return null;
    }

}




