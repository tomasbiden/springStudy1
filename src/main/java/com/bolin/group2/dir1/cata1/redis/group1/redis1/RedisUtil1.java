package com.bolin.group2.dir1.cata1.redis.group1.redis1;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisUtil1 {


    public static RedissonClient createRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");  // Redis服务器地址
        return Redisson.create(config);
    }


    public void bloomFilterMy1(RedissonClient redissonClient){
        // 创建布隆过滤器
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("myBloomFilter");

        // 初始化布隆过滤器，指定预期插入的元素数量和误判率
        bloomFilter.tryInit(1000000, 0.03);  // 预期插入100万条数据，误判率为 3%

        // 向布隆过滤器添加元素
        bloomFilter.add("item1");
        bloomFilter.add("item2");
        bloomFilter.add("item3");

        // 检查元素是否存在
        System.out.println("Contains 'item1': " + bloomFilter.contains("item1")); // true
        System.out.println("Contains 'item4': " + bloomFilter.contains("item4")); // false

    }
    public static void basicDataMy1(RedissonClient redisson){
        RBucket<String> bucket = redisson.getBucket("myString");
        bucket.set("Hello Redisson");          // 写入
        String value = bucket.get();           // 读取
        System.out.println(value);             // 输出: Hello Redisson

        RList<String> list = redisson.getList("myList");
        list.add("A");                        // 尾部添加元素
        list.add(0, "B");                     // 头部插入元素
        List<String> all = list.readAll();    // 获取全部元素
        System.out.println(all);              // 输出: [B, A]

        RSet<String> set = redisson.getSet("mySet");
        set.add("A");
        set.add("B");
        set.add("A");                         // 重复元素自动去重
        Set<String> members = set.readAll();
        System.out.println(members);          // 输出: [A, B]

        RMap<String, String> map = redisson.getMap("myHash");
        map.put("name", "Alice");             // 设置字段
        map.put("age", "30");
        String name = map.get("name");        // 获取字段值
        System.out.println(name);             // 输出: Alice

        RScoredSortedSet<String> zset = redisson.getScoredSortedSet("myZset");
        zset.add(100, "Alice");               // 添加元素并指定分数
        zset.add(90, "Bob");
        Collection<String> top = zset.valueRangeReversed(0, 1);  // 按分数降序取前2名
        System.out.println(top);              // 输出: [Alice, Bob]

        RHyperLogLog<String> hyperLogLog = redisson.getHyperLogLog("myHyperLogLog");
        hyperLogLog.addAll(Arrays.asList("A","B","C","A","B","C","A","B","C","A","B","C","A","B","C","A","B","C","A"));  // 添加元素（自动去重）
        long count = hyperLogLog.count();     // 统计基数
        System.out.println(count);            // 输出: 3

        RBitSet bitmap = redisson.getBitSet("myBitmap");
        bitmap.set(0, true);                  // 设置第0位为1
        bitmap.set(2, true);
        long countBits = bitmap.cardinality();// 统计值为1的位数
        System.out.println(countBits);        // 输出: 2

        RGeo<String> geo = redisson.getGeo("myGeo");
// 添加地理位置（经度、纬度、名称）
        geo.add(new GeoEntry(116.405285, 39.904989, "Beijing"),
                new GeoEntry(121.473701, 31.230416, "Shanghai"));

// 查询北京附近500km内的城市
        List<String> cities = geo.radius(116.405285, 39.904989, 500, GeoUnit.KILOMETERS);
        System.out.println(cities);



    }

    /****
     * 【Redisson RateLimiter 核心知识点】
     * 1. 分布式限流原理：基于Redis的原子操作实现跨JVM的流量控制
     * 2. 令牌桶算法：允许突发流量，平滑过渡到恒定速率
     * 3. 两种限流模式：
     *    - OVERALL：全局模式（所有客户端共享同一个限流器）
     *    - PER_CLIENT：单客户端模式（每个IP/客户端独立限流）
     * 4. 动态配置：支持运行时修改限流规则
     */
    public static void rateLimiterStudy1(RedissonClient redisson){
        try {

            /**** 获取/创建限流器（名称标识唯一限流规则） */
            RRateLimiter rateLimiter = redisson.getRateLimiter("order_api_limiter");

            /**** 初始化限流规则（如果不存在则创建） */
            // 参数说明：RateType, 每秒生成令牌数, 时间间隔, 时间单位, 突发容量（可选）
            boolean isSet = rateLimiter.trySetRate(
                    RateType.OVERALL,
                    5,          // 每秒5个令牌
                    1,
                    RateIntervalUnit.SECONDS
            );
            System.out.println("限流规则设置" + (isSet ? "成功" : "已存在"));

            /**** 场景1：阻塞式获取令牌（适合必须等待的请求） */
            System.out.println("\n--- 测试阻塞式获取 ---");
            for (int i = 1; i <= 3; i++) {
                long start = System.currentTimeMillis();
                rateLimiter.acquire(1);  // 获取1个令牌（阻塞）
                long cost = System.currentTimeMillis() - start;
                System.out.printf("请求%d 等待时间: %dms%n", i, cost);
            }

            /**** 场景2：非阻塞尝试获取（快速失败机制） */
            System.out.println("\n--- 测试非阻塞获取 ---");
            boolean acquired = rateLimiter.tryAcquire(1, 500, TimeUnit.MILLISECONDS);
            if (acquired) {
                System.out.println("非阻塞获取成功");
            } else {
                System.out.println("请求被限流");
            }

            /**** 场景3：批量请求处理（需要多个令牌） */
            System.out.println("\n--- 测试批量获取 ---");
            int requiredTokens = 3;
            if (rateLimiter.tryAcquire(requiredTokens)) {
                System.out.println("成功获取" + requiredTokens + "个令牌，处理批量请求");
            } else {
                System.out.println("令牌不足，无法处理批量请求");
            }

            /**** 场景4：动态调整限流规则（应对流量变化） */
            System.out.println("\n--- 动态调整限流速率 ---");
            rateLimiter.setRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
            System.out.println("已调整限流速率到10/秒");

            /**** 验证新速率生效 */
            System.out.println("\n--- 验证新速率 ---");
            for (int i = 1; i <= 12; i++) {
                if (rateLimiter.tryAcquire()) {
                    System.out.printf("请求%d 成功%n", i);
                } else {
                    System.out.printf("请求%d 被限流%n", i);
                }
                Thread.sleep(50);  // 模拟请求间隔
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            /**** 重要：关闭客户端释放资源 */
            if (redisson != null) {
                redisson.shutdown();
                System.out.println("\nRedis连接已关闭");
            }
        }
    }

    /**
     * 【Redisson分布式锁核心知识体系】
     *
     * 1. 核心作用：解决分布式环境下多节点对共享资源的互斥访问问题
     * 2. 实现原理：
     *    - 基于Redis的SETNX+Lua脚本保证原子性
     *    - 通过看门狗(Watch Dog)机制实现锁自动续期
     *    - 使用Hash结构存储线程ID实现可重入特性
     * 3. 关键特性：
     *    - 可重入锁（Reentrant Lock）
     *    - 公平锁（Fair Lock）
     *    - 联锁（MultiLock）
     *    - 红锁（RedLock）
     * 4. 典型应用场景：
     *    - 分布式定时任务调度
     *    - 库存扣减等并发控制
     *    - 全局配置更新操作
     */
    public class DistributedLockDemo {
        private static final String LOCK_KEY = "order_lock";
        private static RedissonClient redisson;

        public static void main(String[] args) {
            // 初始化Redisson客户端（生产环境应使用连接池）
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379")
                    .setConnectionPoolSize(10);
            redisson = Redisson.create(config);

            // 获取分布式锁对象
            RLock lock = redisson.getLock(LOCK_KEY);

            try {
                // 尝试获取锁（等待5秒，锁持有30秒，时间单位明确指定）
                boolean isLocked = lock.tryLock(5, 30, TimeUnit.SECONDS);

                if (isLocked) {
                    // 临界区操作（模拟业务处理）
                    System.out.println(Thread.currentThread().getName() + " 获得锁，开始处理业务");
                    processBusinessLogic();
                } else {
                    System.out.println("获取锁失败，系统繁忙");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("线程被中断");
            } finally {
                // 确保当前线程持有锁再释放（避免误释放其他线程的锁）
                if (lock != null && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("锁已释放");
                }
                redisson.shutdown();
            }
        }

        private static void processBusinessLogic() throws InterruptedException {
            // 模拟业务处理耗时
            Thread.sleep(2000);
            System.out.println("业务处理完成");
        }
    }

    /**
     * 【五大注意事项】⚠️
     *
     * 1. 锁释放保障：必须使用try-finally确保锁释放，防止死锁
     * 2. 超时时间设置：业务执行时间 < 锁超时时间 < Redis的TTL超时时间
     * 3. 避免长事务：锁范围内代码应保持简短，避免长时间持有锁
     * 4. 锁粒度控制：根据业务场景选择合适粒度的锁（细粒度优先）
     * 5. 异常处理：在finally中释放锁前需判断当前线程是否持有锁
     *
     * 【十大面试问题与回答】💡
     *
     * Q1: Redisson如何实现分布式锁的可重入？
     * A1: 使用Hash结构存储线程ID和重入次数，同一线程每次lock()计数器+1，unlock()计数器-1
     *
     * Q2: 什么是看门狗机制？如何工作？
     * A2: 后台线程每10秒检查锁状态，若未完成业务则自动续期，默认续期到30秒
     *
     * Q3: 如何处理锁超时但业务未完成的情况？
     * A3: 合理设置超时时间+启用看门狗机制，保证业务完成前锁不会过期
     *
     * Q4: 公平锁和普通锁有什么区别？
     * A4: 公平锁通过Redis队列实现FIFO获取顺序，避免线程饥饿
     *
     * Q5: Redis节点宕机时如何保证锁可靠性？
     * A5: 使用RedLock算法，需要大多数(N/2+1)节点获取成功才算真正获取锁
     *
     * Q6: tryLock()和lock()方法的区别？
     * A6: tryLock()非阻塞立即返回结果，lock()会阻塞直到获取锁
     *
     * Q7: 如何防止误删其他线程的锁？
     * A7: 释放锁时校验线程ID，只有锁持有者才能释放
     *
     * Q8: 什么是联锁（MultiLock）？
     * A8: 同时获取多个锁的机制，全部成功才算获取成功，用于复杂资源协调
     *
     * Q9: 分布式锁的性能瓶颈如何优化？
     * A9: ①减小锁粒度 ②设置合理超时 ③使用分段锁 ④异步续期
     *
     * Q10: Redisson锁与ZooKeeper锁的优劣对比？
     * A10: Redis锁性能高但强一致性弱，ZK锁强一致但性能低，根据CAP选择
     */





    public static void main(String[] args){

        RedissonClient redisson = createRedissonClient();
//        basicDataMy1(redisson);
        rateLimiterStudy1(redisson);




    }


}
