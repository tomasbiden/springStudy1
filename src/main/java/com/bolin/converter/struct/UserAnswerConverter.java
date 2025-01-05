package com.bolin.converter.struct;

import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.LogisticsChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAnswerConverter {
    UserAnswerConverter INSTANCE = Mappers.getMapper(UserAnswerConverter.class);

    LogisticsChannel toLogisticsChannel(LogisticsChannelSaveParam param);
}
