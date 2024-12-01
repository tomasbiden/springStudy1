package com.bolin.mapper;

import com.bolin.demos.vo.AppVo1;
import com.bolin.pojo.App;
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
public interface AppMapper extends BaseMapper<App> {
    @Select("SELECT  app.appName ,app.id,question.questionContent from app join yudada.question on  app.id=question.appId where app.id>=app.userId ")
    @ResultMap("AppVo1ResultMap")
    List<AppVo1> selectTest1(@Param("userId") Long userId);

    List<AppVo1> selectTestByXml(@Param("userId") Long userId);

}




