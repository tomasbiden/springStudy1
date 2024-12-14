package com.bolin.converter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bolin.demos.pojo.LogisticsChannel;

public class LogisticsChannelServiceConvertor {

    public static LambdaQueryWrapper<LogisticsChannel> toQueryBylogisticsChannelCode(Long tenantId,String logisticsChannelCode){
        LambdaQueryWrapper<LogisticsChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LogisticsChannel::getTenantId, tenantId);
        queryWrapper.eq(LogisticsChannel::getLogisticsChannelCode, logisticsChannelCode).orderByDesc(LogisticsChannel::getId).last("limit 1");
        return queryWrapper;
    }
}