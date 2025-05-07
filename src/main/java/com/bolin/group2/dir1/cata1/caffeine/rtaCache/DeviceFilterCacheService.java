package com.bolin.group2.dir1.cata1.caffeine.rtaCache;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.map.MapUtil;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author barry.li
 * @since 2025/4/27 11:00
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class DeviceFilterCacheService implements CommandLineRunner {

    private final RtaDeviceFilterRepository rtaDeviceFilterRepository;
    @Qualifier("deviceFilterCaffeineCacheManager")
    private final CaffeineCacheManager cacheManager;

    private static final String UNKNOWN = "UNKNOWN";


    private void initCache() {
        cacheManager.setCacheNames(Lists.newArrayList(SysConst.CACHE_RTA_DEVICE_FILTER));
        loadCache();
    }

    private void loadCache() {
        try {
            long start = System.currentTimeMillis();
            List<RtaDeviceFilter> rtaDeviceFilterList = rtaDeviceFilterRepository.findAll();
            loadCacheByEntitys(rtaDeviceFilterList);
            long end = System.currentTimeMillis();
            log.info("load rta device filter cache cost: {} ms", (end - start));
        } catch (Exception e) {
            log.error("load rta device filter cache error: {}", e.getLocalizedMessage());
            throw new IllegalStateException("load rta device filter cache error: {}", e);
        }
    }


    public void refreshCache(Object key, Object value, RemovalCause cause) {
        if (!RemovalCause.EXPIRED.equals(cause)) {
            return;
        }
        try {
            long start = System.currentTimeMillis();
            List<RtaDeviceFilter> rtaDeviceFilterList = rtaDeviceFilterRepository.findByPlatformName((String) key);
            loadCacheByEntitys(rtaDeviceFilterList);
            long end = System.currentTimeMillis();
            log.info("beacause of " + key + " " + cause.name() + " refresh rta device filter cache cost: {} ms", (end - start));
        } catch (Exception e) {
            log.error("beacause of " + key + " " + cause.name() + " refresh  rta device filter cache error: {}", e.getLocalizedMessage());
            throw new IllegalStateException("beacause of " + key + " " + cause.name() + " refresh  rta device filter cache error: {}", e);
        }
    }

    public Map<String,Set<String>> getRegionDeviceSetByKey(Object key) {
        Map<String, Set<String>> regionDeviceSet = Maps.newConcurrentMap();
        try {
            long start = System.currentTimeMillis();
            List<RtaDeviceFilter> rtaDeviceFilterList = rtaDeviceFilterRepository.findByPlatformName(key.toString());
//        先按RegionCode分组，最后将RtaDeviceFilter 的deviceBrand和deviceModel map成一个String
            Map<String, List<RtaDeviceFilter>> regionCodeDeviceMap = CollStreamUtil.groupByKey(rtaDeviceFilterList, RtaDeviceFilter::getRegionCode);
                regionCodeDeviceMap.forEach((regionCode, regionCodeDeviceList) -> {
                    Set<String> regionCodeDeviceSet = CollStreamUtil.toSet(regionCodeDeviceList, rtaDeviceFilter -> joinDeviceBrandModel(rtaDeviceFilter.getDeviceBrand(), rtaDeviceFilter.getDeviceModel()));
                    regionDeviceSet.put(regionCode, regionCodeDeviceSet);
                });
            long end = System.currentTimeMillis();
            log.info("beacause of " + key + " " + "cache invalidation" + " refresh rta device filter cache cost: {} ms", (end - start));
        } catch (Exception e) {
            log.error("beacause of " + key + " " + "cache invalidation" + " refresh  rta device filter cache error: {}", e.getLocalizedMessage());
            throw new IllegalStateException("beacause of " + key + " " + "cache invalidation" + " refresh  rta device filter cache error: {}", e);
        }
        return regionDeviceSet;
    }


    private void loadCacheByEntitys(List<RtaDeviceFilter> rtaDeviceFilterList) {
        if (CollectionUtils.isEmpty(rtaDeviceFilterList)) {
            return;
        }
//        先按platformName 再按RegionCode分组，最后将RtaDeviceFilter 的deviceBrand和deviceModel map成一个String
        Map<String, Map<String, List<RtaDeviceFilter>>> groupedByPlatformAndRegion = CollStreamUtil.groupBy2Key(rtaDeviceFilterList, RtaDeviceFilter::getPlatformName, RtaDeviceFilter::getRegionCode);
        groupedByPlatformAndRegion.forEach((platformName, regionCodeDeviceMap) -> {
            Map<String, Set<String>> regionDeviceSet = Maps.newConcurrentMap();
            regionCodeDeviceMap.forEach((regionCode, regionCodeDeviceList) -> {
                Set<String> regionCodeDeviceSet = CollStreamUtil.toSet(regionCodeDeviceList, rtaDeviceFilter -> joinDeviceBrandModel(rtaDeviceFilter.getDeviceBrand(), rtaDeviceFilter.getDeviceModel()));
                regionDeviceSet.put(regionCode, regionCodeDeviceSet);
            });
            putCache(SysConst.CACHE_RTA_DEVICE_FILTER, platformName, regionDeviceSet);
        });
    }

    public boolean notLowEndDevice(String platformName, String regionCode, String deviceBrand, String deviceModel) {
        Map<String,Set<String>> regionCodeDeviceMap = getCacheValue(SysConst.CACHE_RTA_DEVICE_FILTER, platformName);
        if(MapUtil.isEmpty(regionCodeDeviceMap)){
            return true;
        }
        Set<String> lowEndDevices = regionCodeDeviceMap.get(regionCode.toUpperCase());
        return !(lowEndDevices != null && lowEndDevices.contains(joinDeviceBrandModel(deviceBrand,deviceModel)));
    }

    public <T> T getCacheValue(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        // 如果缓存未命中，则通过 valueLoader 加载新值并返回, 特别注意：如果key是多个的话会刷库多次，只对key进行加锁
        return (T) cache.get(key, () -> getRegionDeviceSetByKey(key));
    }

    private void putCache(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    private static String preProcessString(String str) {
        if (StringUtil.isBlank(str)) {
            return UNKNOWN;
        }
        return str.trim().toUpperCase();
    }

    private String joinDeviceBrandModel(String deviceBrand, String deviceModel) {
        return  preProcessString(deviceBrand)+"_"+preProcessString(deviceModel);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initCache();
        } catch (Exception e) {
            throw new IllegalStateException("device filter Cache init fail", e);
        }
    }
}
