package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.converter.struct.AppConverter;
import com.bolin.group2.dir1.cata1.demos.pojo.App;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.group2.dir1.cata1.demos.vo.AppWithUserAnswerVo;
import com.bolin.group2.dir1.cata1.service.AppService;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import com.bolin.mapper.AppMapper;
import jakarta.annotation.Resource;
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
    implements AppService {
    @Resource
    private  AppMapper appMapper;

    @Resource
    private UserAnswerService userAnswerService;



    public void  test1(){
        int h=1;
        App app = appMapper.selectById(1);

        return;

    }

    @Override
//    @Transactional
    public void transacionalTest() {


        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();
        appQueryWrapper.eq("user_id",2L);
        List<App> appList = appMapper.selectList(appQueryWrapper);
        App app=appList.get(0);
        app.setId(1234563L);
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

    @Override
    public AppWithUserAnswerVo getAppWithUserAnswerByAppId(Long appId) {

        App appById = this.getById(appId);
        AppWithUserAnswerVo appWithUserAnserVo = AppConverter.INSTANCE.toAppWithUserAnserVo(appById);
        List<UserAnswer> userAnswerByAppId = userAnswerService.getUserAnswerByAppId(appById.getId());
        appWithUserAnserVo .setUserAnswerList(userAnswerByAppId);
        return appWithUserAnserVo;

    }

}




