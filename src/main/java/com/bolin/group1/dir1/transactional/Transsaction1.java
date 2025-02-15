package com.bolin.group1.dir1.transactional;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bolin.mapper.AppMapper;
import com.bolin.group2.dir1.cata1.demos.pojo.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Component
public class Transsaction1 {
    @Autowired
    AppMapper appMapper;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,readOnly = false,timeout = 10,rollbackFor = {RuntimeException.class, SQLException.class})
    public void transactionTest1(){

        App app = appMapper.selectById(1l);
        UpdateWrapper<App> appUpdateWrapper = new UpdateWrapper<>();
        appUpdateWrapper.eq("id",1).set("userId",2);

        appMapper.update(appUpdateWrapper);
//        去除阻塞pacakge代码
//        appMapper.insert(app);



    }

}
