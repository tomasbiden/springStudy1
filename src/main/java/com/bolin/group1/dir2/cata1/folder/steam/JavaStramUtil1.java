package com.bolin.group1.dir2.cata1.folder.steam;

import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class JavaStramUtil1 {

    @Builder
    @Data
    static class Order {
        String userId;
        String product;
        double amount;
        // 构造方法/Getter省略
    }
    @Builder
    @Data
    static class User {
        int id;
        String name;
        String dept;
        // 构造方法/Getter省略
    }

        public static List<Order> testData() {
            return Arrays.asList(
                    new Order("U1", "Phone", 5000),
                    new Order("U1", "Laptop", 8000),
                    new Order("U2", "Tablet", 3000),
                    new Order("U2", "Phone", 6000)
            );
        }

        public static void  toMapAndGrouping(){
            // ==================== groupingBy 案例 ====================
            List<Order> orders = JavaStramUtil1.testData();

            // 案例1：按用户分组订单列表
            Map<String, List<Order>> userOrders = orders.stream()
                    .collect(Collectors.groupingBy(
                            o -> o.userId // 分类依据：用户ID
                    ));
            // 结构：U1->[Order1, Order2], U2->[Order3, Order4]

            // 案例2：按用户分组并计算总金额
            Map<String, Double> userTotal = orders.stream()
                    .collect(Collectors.groupingBy(
                            o -> o.userId,
                            Collectors.summingDouble(o -> o.amount) // 下游收集器：求和
                    ));
            // 结果：U1->13000.0, U2->9000.0

            // 案例3：多级分组（用户+商品类型）
            Map<String, Map<String, List<Order>>> userProductMap = orders.stream()
                    .collect(Collectors.groupingBy(
                            o -> o.userId,
                            Collectors.groupingBy(o -> o.product) // 二级分组
                    ));
            // 结构：U1->{Phone:[...], Laptop:[...]}

            // ==================== toMap 案例 ====================
            List<User> users = Arrays.asList(
                    new User(1, "Alice", "Dev"),
                    new User(2, "Bob", "Test"),
                    new User(3, "Alice", "PM") // 同名故意冲突
            );

            // 案例4：构建ID->用户对象映射
            Map<Integer, User> idToUser = users.stream()
                    .collect(Collectors.toMap(
                            User::getId,   // Key提取器
                            u -> u,        // Value提取器
                            (oldVal, newVal) -> oldVal // 冲突时保留旧值
                    ));
            // 结果：1->Alice(Dev), 2->Bob, 3->Alice(PM)

            // 案例5：处理重复键合并为列表
            Map<String, List<User>> nameUsers = users.stream()
                    .collect(Collectors.toMap(
                            User::getName,
                            Collections::singletonList, // 单个用户转为List
                            (list1, list2) -> {         // 合并策略
                                List<User> merged = new ArrayList<>(list1);
                                merged.addAll(list2);
                                return merged;
                            }
                    ));
            // 结果：Alice->[User1, User3], Bob->[User2]


            /**
             * 重要注意事项：
             * 1. 【空值风险】groupingBy的keyFunction返回null会导致NPE，需提前过滤
             * 2. 【性能陷阱】toMap的mergeFunction频繁创建集合时，考虑预初始化容量
             * 3. 【线程安全】parallelStream中使用toMap应指定ConcurrentHashMap
             * 4. 【合并策略】处理重复键必须提供mergeFunction，否则抛IllegalStateException
             * 5. 【不可变集合】groupingBy默认返回ArrayList，需要不可变集合时用toCollection
             *
             * 高频面试问题（10问10答）：
             * 1. Q: groupingBy和toMap的核心区别是什么？
             *    A: groupingBy专为分组设计，允许多值；toMap强制键唯一，需处理冲突
             *
             * 2. Q: 如何处理toMap的键重复问题？
             *    A: 必须提供mergeFunction，例如(old, new) -> new取最新值
             *
             * 3. Q: 如何用groupingBy实现多级分组？
             *    A: 在下游收集器中嵌套groupingBy，形成Map嵌套结构
             *
             * 4. Q: 如何让groupingBy返回TreeMap？
             *    A: 使用重载方法指定mapFactory：groupingBy(classifier, TreeMap::new, downstream)
             *
             * 5. Q: toMap如何保持插入顺序？
             *    A: 指定第四个参数为LinkedHashMap::new
             *
             * 6. Q: 并行流中使用toMap需要注意什么？
             *    A: 必须使用线程安全的Map如ConcurrentHashMap，否则结果不可预测
             *
             * 7. Q: groupingBy默认用什么集合存储分组结果？
             *    A: 默认ArrayList，可通过toCollection定制如HashSet::new
             *
             * 8. Q: 如何统计每个分组的数量？
             *    A: 使用Collectors.counting()作为下游收集器
             *
             * 9. Q: 什么场景下必须用toMap而不是groupingBy？
             *    A: 需要精确控制键值对映射关系，或需要特殊Map类型时
             *
             * 10. Q: 如何将分组结果的值转为自定义对象？
             *    A: 使用Collectors.mapping转换后结合toCollection收集
             */
        }

        public static void main(String[] args){
            JavaStramUtil1.toMapAndGrouping();
        }




}
