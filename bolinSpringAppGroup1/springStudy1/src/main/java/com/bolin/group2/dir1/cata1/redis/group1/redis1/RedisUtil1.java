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


    public RedissonClient createRedissonClient() {
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
    public void basicDataMy1(RedissonClient redisson){
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
    public  void rateLimiterStudy1(RedissonClient redisson){
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


    /**
     * Redisson 死锁知识点讲解
     *
     * 1. **实际应用场景**:
     * Redisson 是一个分布式 Redis 客户端，提供了基于 Redis 的分布式锁功能。死锁通常出现在多个线程/进程在获取锁的过程中，相互等待对方释放锁，造成程序的阻塞和资源浪费。
     * 在分布式系统中，当使用 Redisson 分布式锁时，如果没有正确地使用锁，可能会发生死锁。例如，多个服务或者线程请求相同资源时，如果锁的申请与释放没有正确的顺序或超时设置，会导致死锁现象。
     *
     * 2. **案例**:
     * 典型的死锁案例通常发生在多个服务或者线程在持有多个锁时，由于获取锁的顺序不一致导致死锁。
     * 假设我们有两个锁：lock1 和 lock2，线程 A 获取了 lock1 锁，线程 B 获取了 lock2 锁，但是两个线程都在等待对方释放另一个锁，最终导致死锁。
     * 使用 Redisson 时，如果不设定合适的锁超时、锁的顺序等，就会发生类似问题。
     *
     * 3. **Redisson 锁使用的基本原理**:
     * Redisson 提供了 ReentrantLock 和 RLock 等分布式锁的实现。默认情况下，它支持分布式锁、锁超时、锁重入、互斥性等特性。Redisson 锁是基于 Redis 实现的，采用了 Redis 的 setnx 命令来确保锁的唯一性，确保分布式系统中的同步。
     * 如果没有设置锁的超时或者没有按照正确的顺序获取锁，就会导致死锁。
     *
     * 4. **解决方案**:
     * 为避免死锁，使用 Redisson 锁时，建议：
     * - 为每个锁设置超时时间，避免死锁。
     * - 获取多个锁时，保持获取锁的顺序一致。
     * - 使用 tryLock 方法设置最大等待时间，避免长时间等待。
     * - 使用锁的自动解锁机制，避免忘记解锁。
     */



    public void redissonDeadlockExample(RedissonClient redisson) throws InterruptedException {
        // 获取分布式锁
        RLock lock1 = redisson.getLock("lock1");
        RLock lock2 = redisson.getLock("lock2");

        // 模拟两个线程的死锁场景
        // 线程 A
        Thread threadA = new Thread(() -> {
            try {
                lock1.tryLock(3,10,TimeUnit.SECONDS); // 获取 lock1
                System.out.println("Thread A acquired lock1.");
                Thread.sleep(1000); // 模拟一些业务逻辑
                lock2.lock(); // 尝试获取 lock2，此时 thread B 已持有 lock2，导致死锁
                System.out.println("Thread A acquired lock2.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        });

        // 线程 B
        Thread threadB = new Thread(() -> {
            try {
                lock2.lock(); // 获取 lock2
                System.out.println("Thread B acquired lock2.");
                Thread.sleep(1000); // 模拟一些业务逻辑
                lock1.lock(); // 尝试获取 lock1，此时 thread A 已持有 lock1，导致死锁
                System.out.println("Thread B acquired lock1.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        });

        // 启动线程 A 和线程 B
        threadA.start();
        threadB.start();

        // 等待线程完成
        threadA.join();
        threadB.join();

        redisson.shutdown();
    }

    /**
     * 5个注意点:
     * 1. **避免锁的交叉依赖**: 多个线程获取多个锁时，确保锁的获取顺序一致，避免死锁。
     * 2. **锁超时设置**: 使用锁时要设置锁的超时值，避免长期等待。
     * 3. **tryLock**: 使用 `tryLock` 来设置获取锁的最大等待时间，避免死锁。
     * 4. **定期解锁**: 确保每个锁在不再需要时能够及时释放，避免长时间占用资源。
     * 5. **死锁检测**: Redisson 本身不提供死锁检测，但可以通过业务逻辑检测是否有线程长时间占用锁并进行处理。

     * 常见的面试问题:
     * 1. **Redisson 如何实现分布式锁?**
     *    - 使用 Redis 的 `setnx` 命令保证锁的唯一性，通过 Redisson 封装为分布式锁。
     * 2. **如何避免 Redisson 死锁?**
     *    - 保证锁获取顺序一致，设置锁超时时间，使用 `tryLock` 来避免长时间等待。
     * 3. **Redisson 锁的超时设置有什么意义?**
     *    - 防止一个线程长时间占用锁，导致其他线程无法获取锁，从而避免死锁或性能问题。
     * 4. **Redisson 的 `RLock` 和 `ReentrantLock` 有什么区别?**
     *    - `RLock` 是 Redisson 提供的分布式锁，实现了 Redis 分布式环境下的可重入特性。
     * 5. **Redisson 锁的 `tryLock` 是如何使用的?**
     *    - `tryLock` 可以设置最大等待时间和锁持有时间，用于避免死锁。
     * 6. **Redisson 锁的 `lockInterruptibly` 方法的作用是什么?**
     *    - `lockInterruptibly` 允许线程在等待锁时响应中断。
     * 7. **Redisson 锁是否支持公平锁?**
     *    - Redisson 支持公平锁，通过 `FairLock` 来确保线程按照请求锁的顺序获取锁。
     * 8. **Redisson 锁的自动解锁机制是怎样的?**
     *    - Redisson 锁会在锁超时后自动释放，避免忘记手动解锁。
     * 9. **如果分布式锁获取失败，应该如何处理?**
     *    - 可以使用 `tryLock`，在获取锁失败时设置合适的处理机制，比如重试或放弃。
     * 10. **如何保证分布式锁的可伸缩性?**
     *    - 通过合理配置 Redisson 集群，保证在高并发情况下锁的性能与稳定性。
     */


    // 1. 阻塞等待 + 看门狗自动续期
    public static void methodWithLock(RedissonClient redissonClient) {
        RLock lock = redissonClient.getLock("resourceLock");
        lock.lock(); // 阻塞直到获取锁，默认30秒过期，看门狗自动续期
        try {
            // 业务逻辑（如数据库操作）
            System.out.println("执行需要长期持有的任务...");
        } finally {
            lock.unlock(); // 必须手动释放
        }
    }

    // 2. 指定等待时间 + 看门狗续期
    public static void methodWithTryLock(RedissonClient redissonClient) throws InterruptedException {
        RLock lock = redissonClient.getLock("resourceLock");
        boolean isLocked = lock.tryLock(5, TimeUnit.SECONDS); // 最多等5秒
        if (isLocked) {
            try {
                // 业务逻辑（如支付回调处理）
                System.out.println("获取锁成功，处理业务...");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("等待超时，执行降级策略");
        }
    }

    // 3. 指定等待时间和固定过期时间
    public static void methodWithTryLockAndLease(RedissonClient redissonClient) throws InterruptedException {
        RLock lock = redissonClient.getLock("resourceLock");
        boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS); // 等3秒，锁10秒后自动过期
        if (isLocked) {
            try {
                // 业务逻辑（如库存扣减）
                System.out.println("获取锁成功，10秒内完成业务...");
            } finally {
                lock.unlock();
            }
        }
    }

    // 4. 立即获取锁 + 固定过期时间
    public static void methodWithLockAndLease(RedissonClient redissonClient) {
        RLock lock = redissonClient.getLock("resourceLock");
        lock.lock(30, TimeUnit.SECONDS); // 立即获取，30秒后自动释放
        try {
            // 业务逻辑（如文件处理）
            System.out.println("立即执行且30秒内完成...");
        } finally {
            lock.unlock();
        }
    }


/**
 * 注意事项：
 * 1. 【强制】解锁必须放在finally块，避免死锁
 * 2. 【推荐】使用tryLock时需处理InterruptedException（如线程中断）
 * 3. 【警告】leaseTime必须大于业务执行时间，否则提前释放导致数据不一致
 * 4. 【建议】看门狗机制适合无法预估时间的场景，但会增加Redis压力
 * 5. 【最佳实践】锁名称需唯一标识资源（如order_123）
 *
 * 常见面试问题与回答：
 * 1. Q: Redisson的看门狗机制是什么？
 *    A: 后台线程每隔10秒检查锁状态，若未释放则重置过期时间到30秒
 *
 * 2. Q: tryLock(5,10,TimeUnit.SECONDS)参数含义？
 *    A: 最多等待5秒获取锁，锁持有10秒后自动过期
 *
 * 3. Q: 为什么需要手动unlock()？
 *    A: 防止线程终止时锁无法自动释放（看门狗依赖线程存活）
 *
 * 4. Q: Redisson锁如何实现可重入？
 *    A: 通过计数器记录线程获取次数，unlock()时递减直到0释放
 *
 * 5. Q: 锁过期时间和业务执行时间的关系？
 *    A: 若使用固定leaseTime，必须确保业务在时间内完成
 *
 * 6. Q: Redis主从切换会导致锁失效吗？
 *    A: 可能（RedLock可缓解但不完全解决），需结合业务容错
 *
 * 7. Q: 如何避免锁被其他线程释放？
 *    A: 锁关联客户端ID，释放时校验身份
 *
 * 8. Q: lock()和tryLock()的性能差异？
 *    A: lock()会阻塞线程，高并发时建议用tryLock避免线程堆积
 *
 * 9. Q: 锁自动续期失败的原因？
 *    A: Redis节点宕机/网络中断，或看门狗线程被终止
 *
 * 10. Q: 如何实现公平锁？
 *     A: 使用getFairLock()，基于Redis队列实现先进先出
 */


    /**
     * 四种锁使用频率排序及原因分析：
     *
     * 1. ​**tryLock(long waitTime, long leaseTime, TimeUnit unit)**​ 👑（使用最多）
     *    - 原因：在高并发场景中，通过明确指定「最大等待时间」和「锁过期时间」，能更好地平衡系统性能和可靠性：
     *      - ✅ 避免线程无限等待（设置waitTime防止线程堆积）
     *      - ✅ 防止锁因异常未释放（leaseTime兜底自动过期）
     *      - ✅ 适用于大部分业务场景（如库存扣减、订单处理）
     *
     * 2. ​**lock(long leaseTime, TimeUnit unit)**​ 🥈
     *    - 原因：适用于需要立即获取锁且业务时间可预估的场景：
     *      - ✅ 无等待时间，适合高优先级任务（如抢购资格锁定）
     *      - ✅ leaseTime强制约束业务执行时间（如文件处理限时30秒）
     *      - ❌ 需精确评估业务耗时，否则易出现锁提前释放
     *
     * 3. ​**tryLock(long waitTime, TimeUnit unit)**​ 🥉
     *    - 原因：允许短暂等待但依赖看门狗续期：
     *      - ✅ 适合业务时间波动但总体可控（如支付回调处理）
     *      - ❌ 看门狗机制可能增加Redis负担（需持续续期）
     *      - ❌ 若线程异常终止可能导致锁泄漏（需完善错误处理）
     *
     * 4. ​**lock()**​ ⚠️（谨慎使用）
     *    - 原因：完全依赖看门狗续期，风险较高：
     *      - ✅ 唯一适合「无法预估执行时间」的场景（如长事务）
     *      - ❌ 线程阻塞可能导致系统吞吐量下降
     *      - ❌ Redis网络波动时易导致锁续期失败
     *
     * 为什么 tryLock(waitTime, leaseTime) 使用最多？案例说明：
     * 假设电商系统中「库存扣减」场景：
     * - 设置 waitTime=3秒（避免前端用户等待过久）
     * - 设置 leaseTime=10秒（扣减操作必须在10秒内完成）
     * - 若3秒内未获取锁 → 返回"库存繁忙"提示
     * - 若获取锁但10秒未完成 → 锁自动释放防止死锁
     * → 完美平衡用户体验和系统安全
     */






    public static void main(String[] args) throws InterruptedException {
        RedisUtil1 redisUtil1 = new RedisUtil1();

        RedissonClient redisson = redisUtil1.createRedissonClient();
//        basicDataMy1(redisson);
//        rateLimiterStudy1(redisson);
        redisUtil1.redissonDeadlockExample(redisson);

        Thread.sleep(50000);




    }


}
