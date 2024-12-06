package com.bolin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.demos.web.AppController;
import com.bolin.pojo.App;
import com.bolin.service.AppService;
import com.bolin.mapper.AppMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Administrator
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2024-11-30 14:26:59
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{
    @Resource
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

    public void queryWrapperSql(){
        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();
        appQueryWrapper.ge("id",1);
        String string = appQueryWrapper.toString();
//        两种利用mapper方式
        List<App> apps = appMapper.selectList(appQueryWrapper);
        List<App> list = this.list(appQueryWrapper);

        int h=1;

    }

}




