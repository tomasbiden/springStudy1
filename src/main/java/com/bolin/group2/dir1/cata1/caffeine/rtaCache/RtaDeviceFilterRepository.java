package com.bolin.group2.dir1.cata1.caffeine.rtaCache;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author barry.li
 * @since 2025/4/27
 */
public interface RtaDeviceFilterRepository extends JpaRepository<RtaDeviceFilter, Integer> {


    List<RtaDeviceFilter> findByPlatformName(String platform);
}
