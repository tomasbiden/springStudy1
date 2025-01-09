package com.bolin.redis.utils;


import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * redis 工具类
 *
 * @author Lion Li
 * @version 3.1.0 新增
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisUtils {

//    private  final RedissonClient redissonClient = ApplicationContext.getBean(RedissonClient.class);
    
    @Autowired
    RedissonClient redissonClient;

    /**
     * 限流
     *
     * @param key          限流key
     * @param rateType     限流类型
     * @param rate         速率
     * @param rateInterval 速率间隔
     * @return -1 表示失败
     */
    public  long rateLimiter(String key, RateType rateType, int rate, int rateInterval) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, rate, rateInterval, RateIntervalUnit.SECONDS);
        if (rateLimiter.tryAcquire()) {
            return rateLimiter.availablePermits();
        } else {
            return -1L;
        }
    }

    public Boolean isRateLimiterExist(String key) {

        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

         return rateLimiter.isExists();
    }


    public  long rateLimiterReBoot(String key,  RateType rateType, int count, int rateInterval){
        if(count <1){
            throw new RuntimeException();
        }
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        //如果不存在rateLimiter
        if(!rateLimiter.isExists()){
            rateLimiter.trySetRate(rateType, count, rateInterval, RateIntervalUnit.SECONDS);
            if (rateLimiter.tryAcquire()) { //返回true 表示可以获得，那么就是没有 触发
                return rateLimiter.availablePermits();
            }else {
                return -1L;
            }

        }

        RateLimiterConfig config = rateLimiter.getConfig();
        Long rateInterval1 = config.getRateInterval();
        Long rate = config.getRate();
        if(TimeUnit.MILLISECONDS.convert(rateInterval,TimeUnit.SECONDS)!=rateInterval1 || count != rate){
            rateLimiter.delete();
            rateLimiter.trySetRate(rateType, count, rateInterval, RateIntervalUnit.SECONDS);
        }
        if (rateLimiter.tryAcquire()) { //返回true 表示可以获得，那么就是没有 触发
            return rateLimiter.availablePermits();
        }else {
            return -1L;
        }
    }

    /**
     * @Description: 限流增强，对重启程序时重置限流规则
     * @Param: key 要限流的key
     * @Param: count  周期数
     * @Param: rateInterval  速率周期时间
     * @return: long
     * @Author: Sevin.Guan
     * @Date: 2024/7/24
     */
    public  long rateLimiterReBoot(String key,  int count, int rateInterval){
        return    rateLimiterReBoot(key,RateType.OVERALL,count,rateInterval);
    }







    /**
     * 获取客户端实例
     */
    public  RedissonClient getClient() {
        return redissonClient;
    }

    /**
     * 发布通道消息
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     * @param consumer   自定义处理
     */
    public  <T> void publish(String channelKey, T msg, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
        consumer.accept(msg);
    }

    public  <T> void publish(String channelKey, T msg) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.publish(msg);
    }

    /**
     * 订阅通道接收消息
     *
     * @param channelKey 通道key
     * @param clazz      消息类型
     * @param consumer   自定义处理
     */
    public  <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = redissonClient.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public  <T> void setCacheObject(final String key, final T value) {
        setCacheObject(key, value, false);
    }

    /**
     * 缓存基本的对象，保留当前对象 TTL 有效期
     *
     * @param key       缓存的键值
     * @param value     缓存的值
     * @param isSaveTtl 是否保留TTL有效期(例如: set之前ttl剩余90 set之后还是为90)
     * @since Redis 6.X 以上使用 setAndKeepTTL 兼容 5.X 方案
     */
    public  <T> void setCacheObject(final String key, final T value, final boolean isSaveTtl) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        if (isSaveTtl) {
            try {
                bucket.setAndKeepTTL(value);
            } catch (Exception e) {
                long timeToLive = bucket.remainTimeToLive();
                setCacheObject(key, value, Duration.ofMillis(timeToLive));
            }
        } else {
            bucket.set(value);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     */
    public  <T> void setCacheObject(final String key, final T value, final Duration duration) {
        RBatch batch = redissonClient.createBatch();
        RBucketAsync<T> bucket = batch.getBucket(key);
        bucket.setAsync(value);
        bucket.expireAsync(duration);
        batch.execute();
    }

    /**
     * 注册对象监听器
     * <p>
     * key 监听器需开启 `notify-keyspace-events` 等 redis 相关配置
     *
     * @param key      缓存的键值
     * @param listener 监听器配置
     */
    public  <T> void addObjectListener(final String key, final ObjectListener listener) {
        RBucket<T> result = redissonClient.getBucket(key);
        result.addListener(listener);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public  boolean expire(final String key, final long timeout) {
        return expire(key, Duration.ofSeconds(timeout));
    }

    /**
     * 设置有效时间
     *
     * @param key      Redis键
     * @param duration 超时时间
     * @return true=设置成功；false=设置失败
     */
    public  boolean expire(final String key, final Duration duration) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.expire(duration);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public  <T> T getCacheObject(final String key) {
        RBucket<T> rBucket = redissonClient.getBucket(key);
        return rBucket.get(); //这里会json 解码
    }

    /**
     * 获得key剩余存活时间
     *
     * @param key 缓存键值
     * @return 剩余存活时间
     */
    public  long getTimeToLive(final String key) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.remainTimeToLive();
    }


    /**
     * 获得key剩余存活时间
     *
     * @param key 缓存键值
     * @return 剩余存活时间
     */
    public  long getExpireTime(final String key) {
        RBucket rBucket = redissonClient.getBucket(key);
        return rBucket.getExpireTime();
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存的键值
     */
    public  boolean deleteObject(final String key) {
        return redissonClient.getBucket(key).delete();
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    public  void deleteObject(final Collection collection) {
        RBatch batch = redissonClient.createBatch();
        collection.forEach(t -> {
            batch.getBucket(t.toString()).deleteAsync();
        });
        batch.execute();
    }

    /**
     * 检查缓存对象是否存在
     *
     * @param key 缓存的键值
     */
    public  boolean isExistsObject(final String key) {
        return redissonClient.getBucket(key).isExists();
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public  <T> boolean setCacheList(final String key, final List<T> dataList) {
        RList<T> rList = redissonClient.getList(key);
        return rList.addAll(dataList);
    }

    /**
     * 注册List监听器
     * <p>
     * key 监听器需开启 `notify-keyspace-events` 等 redis 相关配置
     *
     * @param key      缓存的键值
     * @param listener 监听器配置
     */
    public  <T> void addListListener(final String key, final ObjectListener listener) {
        RList<T> rList = redissonClient.getList(key);
        rList.addListener(listener);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public  <T> List<T> getCacheList(final String key) {
        RList<T> rList = redissonClient.getList(key);
        return rList.readAll();
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public  <T> boolean setCacheSet(final String key, final Set<T> dataSet) {
        RSet<T> rSet = redissonClient.getSet(key);
        return rSet.addAll(dataSet);
    }

    /**
     * 注册Set监听器
     * <p>
     * key 监听器需开启 `notify-keyspace-events` 等 redis 相关配置
     *
     * @param key      缓存的键值
     * @param listener 监听器配置
     */
    public  <T> void addSetListener(final String key, final ObjectListener listener) {
        RSet<T> rSet = redissonClient.getSet(key);
        rSet.addListener(listener);
    }

    /**
     * 获得缓存的set
     *
     * @param key 缓存的key
     * @return set对象
     */
    public  <T> Set<T> getCacheSet(final String key) {
        RSet<T> rSet = redissonClient.getSet(key);
        return rSet.readAll();
    }

    /**
     * 缓存Map
     *
     * @param key     缓存的键值
     * @param dataMap 缓存的数据
     */
    public  <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            RMap<String, T> rMap = redissonClient.getMap(key);
            rMap.putAll(dataMap);
        }
    }

    /**
     * 注册Map监听器
     * <p>
     * key 监听器需开启 `notify-keyspace-events` 等 redis 相关配置
     *
     * @param key      缓存的键值
     * @param listener 监听器配置
     */
    public  <T> void addMapListener(final String key, final ObjectListener listener) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.addListener(listener);
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存的键值
     * @return map对象
     */
    public  <T> Map<String, T> getCacheMap(final String key) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.getAll(rMap.keySet());
    }

    /**
     * 获得缓存Map的key列表
     *
     * @param key 缓存的键值
     * @return key列表
     */
    public  <T> Set<String> getCacheMapKeySet(final String key) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.keySet();
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public  <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        rMap.put(hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public  <T> T getCacheMapValue(final String key, final String hKey) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.get(hKey);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public  <T> T delCacheMapValue(final String key, final String hKey) {
        RMap<String, T> rMap = redissonClient.getMap(key);
        return rMap.remove(hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public  <K, V> Map<K, V> getMultiCacheMapValue(final String key, final Set<K> hKeys) {
        RMap<K, V> rMap = redissonClient.getMap(key);
        return rMap.getAll(hKeys);
    }

    /**
     * 设置原子值
     *
     * @param key   Redis键
     * @param value 值
     */
    public  void setAtomicValue(String key, long value) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        atomic.set(value);
    }

    /**
     * 获取原子值
     *
     * @param key Redis键
     * @return 当前值
     */
    public  long getAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.get();
    }

    /**
     * 递增原子值
     *
     * @param key Redis键
     * @return 当前值
     */
    public  long incrAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.incrementAndGet();
    }

    /**
     * 递减原子值
     *
     * @param key Redis键
     * @return 当前值
     */
    public  long decrAtomicValue(String key) {
        RAtomicLong atomic = redissonClient.getAtomicLong(key);
        return atomic.decrementAndGet();
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public  Collection<String> keys(final String pattern) {
        Stream<String> stream = redissonClient.getKeys().getKeysStreamByPattern(pattern);
        return stream.collect(Collectors.toList());
    }

    /**
     * 删除缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     */
    public  void deleteKeys(final String pattern) {
        redissonClient.getKeys().deleteByPattern(pattern);
    }

    /**
     * 检查redis中是否存在key
     *
     * @param key 键
     */
    public  Boolean hasKey(String key) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.countExists(key) > 0;
    }
}
