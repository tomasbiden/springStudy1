package com.bolin.group2.dir1.cata1.caffeine.rtaCache;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.Instant;


/**
 * @author barry.li
 * @since 2025/4/27
 */
@Getter
@Setter
@Entity
@Table(name = "rta_device_filter")
@Where(clause = "active = 1")
public class RtaDeviceFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "platform_name")
    private String platformName;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "device_brand")
    private String deviceBrand;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;


}