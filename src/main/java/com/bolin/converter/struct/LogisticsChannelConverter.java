package com.bolin.converter.struct;

import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.LogisticsChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogisticsChannelConverter {
    LogisticsChannelConverter INSTANCE = Mappers.getMapper(LogisticsChannelConverter.class);

    LogisticsChannel toLogisticsChannel(LogisticsChannelSaveParam param);
}
