package com.bolin.demos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogisticsChannelSaveParam {
    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 物流渠道编码
     */
    private String logisticsChannelCode;

    /**
     * 物流渠道名称
     */
    private String channelName;

    /**
     * 物流商账户名称
     */
    private String accountName;

    /**
     * 承运商编码
     */
    private String carrierCode;

    /**
     * 保险服务
     */
    private Integer insuranceService;

    /**
     * 签名服务
     */
    private Integer signatureService;

    /**
     * 状态：true为启用，false为禁用
     */
    private Integer status;
}
