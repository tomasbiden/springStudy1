package com.bolin.group2.dir1.cata1.converter.struct;

import com.bolin.group2.dir1.cata1.demos.dto.LogisticsChannelSaveParam;
import com.bolin.group2.dir1.cata1.demos.pojo.LogisticsChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogisticsChannelConverter {
    LogisticsChannelConverter INSTANCE = Mappers.getMapper(LogisticsChannelConverter.class);

    LogisticsChannel toLogisticsChannel(LogisticsChannelSaveParam param);
}
