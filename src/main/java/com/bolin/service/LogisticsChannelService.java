package com.bolin.service;

import com.bolin.demos.dto.LogisticsChannelSaveParam;
import com.bolin.demos.pojo.LogisticsChannel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【logistics_channel(物流渠道表)】的数据库操作Service
* @createDate 2024-12-14 13:20:42
*/
public interface LogisticsChannelService extends IService<LogisticsChannel> {
    public Long save(LogisticsChannelSaveParam param);


}
