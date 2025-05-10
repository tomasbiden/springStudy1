package com.bolin.mapper;

import com.bolin.group2.dir1.cata1.demos.vo.AppVo1;
import com.bolin.group2.dir1.cata1.demos.pojo.App;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【app(应用)】的数据库操作Mapper
* @createDate 2024-11-30 14:26:59
* @Entity com.bolin.pojo.App
*/
//@Mapper
public interface AppMapper extends BaseMapper<App> {
    @Select("SELECT  app.id as id,app.appName ,question.id as questionId,question.questionContent,question.createTime,user.id,user.userPassword\n" +
            "from app\n" +
            "    join yudada.question on  app.id=question.appId\n" +
            "    join yudada.user on app.userId=user.id\n" +
            "where app.id=1;")
    @ResultMap("AppVo1ResultMap")
    List<AppVo1> selectTest1(@Param("userId") Long userId);

    List<AppVo1> selectTestByXml(@Param("userId") Long userId);

}




