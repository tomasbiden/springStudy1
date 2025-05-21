package com.bolin.group2.dir1.cata1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.group2.dir1.cata1.demos.dto.LogisticsChannelSaveParam;
import com.bolin.group2.dir1.cata1.demos.pojo.LogisticsChannel;
import com.bolin.group2.dir1.cata1.service.LogisticsChannelService;
import com.bolin.mapper.LogisticsChannelMapper;
import org.springframework.stereotype.Service;

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
        LambdaQueryWrapper<LogisticsChannel> queryBylogisticsChannelCode = LogisticsChannelServiceConverter.toQueryBylogisticsChannelCode(param.getTenantId(), param.getLogisticsChannelCode());
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




