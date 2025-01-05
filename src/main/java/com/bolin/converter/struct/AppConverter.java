package com.bolin.converter.struct;

import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.App;
import com.bolin.demos.pojo.LogisticsChannel;
import com.bolin.demos.vo.AppWithUserAnswerVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppConverter {
    AppConverter INSTANCE = Mappers.getMapper(AppConverter.class);

    LogisticsChannel toLogisticsChannel(LogisticsChannelSaveParam param);

    AppWithUserAnswerVo toAppWithUserAnserVo(App app);
}
