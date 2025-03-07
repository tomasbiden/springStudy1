package com.bolin.group1.dir2.cata1.folder.javaDesign.BehavioralPatterns.strategy.group1.dir1.cata1;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式实战案例：支付方式选择场景
 * 核心作用：将算法族抽象为统一接口，实现不同策略的灵活切换，避免多重条件判断。
 * 适用场景：
 * 1. 支付方式选择（微信/支付宝/银联等）
 * 2. 促销活动计算（满减/折扣/立减等）
 * 3. 数据压缩策略（ZIP/RAR/7Z等）
 * 4. 表单验证规则（手机号/邮箱/身份证等）
 */
public class StrategyPatternDemo {

    // ==================== 策略接口定义 ====================
    interface PaymentStrategy {
        void processPayment(double amount); // 支付处理接口
    }

    // ==================== 具体策略实现 ====================
    static class WechatPayment implements PaymentStrategy {
        @Override
        public void processPayment(double amount) {
            System.out.printf("微信支付成功：%.2f 元%n", amount);
            // 实际对接微信支付API的逻辑...
        }
    }

    static class AlipayPayment implements PaymentStrategy {
        @Override
        public void processPayment(double amount) {
            System.out.printf("支付宝支付成功：%.2f 元%n", amount);
            // 实际对接支付宝API的逻辑...
        }
    }

    // ==================== 策略上下文 ====================
    static class PaymentContext {
        private static final Map<String, PaymentStrategy> strategies = new HashMap<>();

        // 初始化注册策略
        static {
            registerStrategy("wechat", new WechatPayment());
            registerStrategy("alipay", new AlipayPayment());
        }

        // 策略注册方法
        public static void registerStrategy(String type, PaymentStrategy strategy) {
            strategies.put(type, strategy);
        }

        // 策略执行方法
        public static void executePayment(String type, double amount) {
            PaymentStrategy strategy = strategies.get(type);
            if (strategy == null) {
                throw new IllegalArgumentException("不支持的支付方式: " + type);
            }
            strategy.processPayment(amount);
        }
    }

    // ==================== 使用示例 ====================
    static void demoPaymentStrategy() {
        // 模拟前端传递支付类型参数
        String paymentType = "wechat";
        double orderAmount = 299.99;

        // 执行策略调用
        PaymentContext.executePayment(paymentType, orderAmount);
    }
}

/**
 * 策略模式5大注意事项：
 * 1. 接口设计原则：策略接口需要足够抽象，避免包含具体实现细节
 * 2. 避免策略膨胀：当策略过多时考虑使用工厂模式进行管理
 * 3. 状态管理：策略对象尽量设计为无状态，必须保留状态时考虑线程安全
 * 4. 性能权衡：高频调用场景需评估策略对象的创建成本
 * 5. 上下文设计：上下文类应保持轻量，避免承担过多业务逻辑
 * 
 * 10个常见面试问题与回答：
 * 1. Q: 什么是策略模式？
 *    A: 定义算法族并封装为独立对象，使它们可以相互替换，主要解决多重条件判断的代码臃肿问题
 * 
 * 2. Q: 策略模式的优点？
 *    A: 提高扩展性（新增策略不影响主体代码）、消除条件语句、提高代码复用性
 * 
 * 3. Q: 策略模式的缺点？
 *    A: 策略类数量可能过多、客户端需要了解所有策略、增加对象创建开销
 * 
 * 4. Q: 与工厂模式的区别？
 *    A: 工厂关注对象创建，策略关注行为选择。两者常结合使用，工厂生成策略对象
 * 
 * 5. Q: 如何实现策略的动态切换？
 *    A: 通过上下文类维护策略对象引用，提供setter方法修改当前策略
 * 
 * 6. Q: 策略模式在Spring中的应用？
 *    A: 通过@Qualifier指定策略实现，或使用Map自动注入所有策略实现
 * 
 * 7. Q: 如何处理策略类需要不同参数的情况？
 *    A: 使用上下文对象包装参数，或通过建造者模式构造策略对象
 * 
 * 8. Q: 策略模式如何保证线程安全？
 *    A: 策略类设计为无状态，或使用ThreadLocal管理状态
 * 
 * 9. Q: 何时不应该使用策略模式？
 *    A: 策略算法非常简单且稳定不变，或策略数量少于3个时
 * 
 * 10. Q: 如何管理大量策略类？
 *     A: 使用模块化分包（按业务领域）、配置化加载、结合责任链模式
 */