package com.bolin.group2.dir1.cata1.redis.group1.redis1;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;


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


    public static void main(String[] args){

        RedissonClient redisson = createRedissonClient();
        basicDataMy1(redisson);



    }
}
