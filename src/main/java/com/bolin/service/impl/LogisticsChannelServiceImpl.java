package com.bolin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.converter.LogisticsChannelServiceConvertor;
import com.bolin.converter.struct.LogisticsChannelConverter;
import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.LogisticsChannel;
import com.bolin.service.LogisticsChannelService;
import com.bolin.mapper.LogisticsChannelMapper;
import jodd.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【logistics_channel(物流渠道表)】的数据库操作Service实现
* @createDate 2024-12-14 13:20:42
*/
@Service
public class LogisticsChannelServiceImpl extends ServiceImpl<LogisticsChannelMapper, LogisticsChannel>
    implements LogisticsChannelService{

    @Override
    public Long save(LogisticsChannelSaveParam param) {
        /*
        LambdaQueryWrapper<LogisticsChannel> queryBylogisticsChannelCode = LogisticsChannelServiceConvertor.toQueryBylogisticsChannelCode(param.getTenantId(), param.getLogisticsChannelCode());
        List<LogisticsChannel> logisticsChannels = getBaseMapper().selectList(queryBylogisticsChannelCode);
        if(CollectionUtils.isEmpty(logisticsChannels)){
            LogisticsChannel logisticsChannel = LogisticsChannelConverter.INSTANCE.toLogisticsChannel(param);
            this.save(logisticsChannel);
            Long id = logisticsChannel.getId();
            return  logisticsChannel.getId();
        }else {
            return null;
        }

         */
        return 1L;

    }
}




