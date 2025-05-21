package com.bolin.group2.dir1.cata1.caffeine.rtaCache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author jack.wen
 * @since 2025/2/23 16:15
 */
@Configuration
@RequiredArgsConstructor
public class CacheConfig {


    private final DeviceFilterCacheService deviceFilterCacheService;

    @Bean("caffeineCacheManager")
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(1)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofSeconds(10))
//                .removalListener((key, value, cause) -> System.out.println("key:" + key + " value:" + value + " cause:" + cause))
                .recordStats());
        return cacheManager;
    }
    @Bean("deviceFilterCaffeineCacheManager")
    public CaffeineCacheManager deviceFilterCaffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(1)
                .maximumSize(20)
                .expireAfterWrite(Duration.ofSeconds(30))
//                已经有 cache.get(key, () -> getRegionDeviceSetByKey(key)) 来替代refresh作用了
//                 .removalListener((key, value, cause) -> deviceFilterCacheService.refreshCache(key,value,cause))
                .recordStats());
        return cacheManager;
    }

    /**
     * 适用场景 用ForkJoinPool.commonPool()  非阻塞异步
     * @return
     */
    @Bean("deviceFilterCaffeineCacheManager2")
    public CaffeineCacheManager deviceFilterCaffeineCacheManager2() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(1)
                .maximumSize(20)
//                已经有 cache.get(key, () -> getRegionDeviceSetByKey(key)) 来替代refresh作用了
//                 .removalListener((key, value, cause) -> deviceFilterCacheService.refreshCache(key,value,cause))
                .refreshAfterWrite(Duration.ofSeconds(30))
                .recordStats());
        cacheManager.setCacheLoader(key -> deviceFilterCacheService.getRegionDeviceSetByKey(key));
        return cacheManager;
    }

}
