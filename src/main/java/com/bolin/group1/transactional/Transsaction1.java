package com.bolin.group1.transactional;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bolin.mapper.AppMapper;
import com.bolin.pojo.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Transsaction1 {
    @Autowired
    AppMapper appMapper;

    @Transactional
    public void transactionTest1(){

        App app = appMapper.selectById(1l);
        UpdateWrapper<App> appUpdateWrapper = new UpdateWrapper<>();
        appUpdateWrapper.eq("id",1).set("userId",2);

        appMapper.update(appUpdateWrapper);

        appMapper.insert(app);



    }

}
