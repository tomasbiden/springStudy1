package com.bolin.group1.dir2.cata1.folder.javaDesign.BehavioralPatterns.strategy.group1.dir1.cata1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring版策略模式实战：基于依赖注入的策略管理
 * 核心升级：利用Spring容器管理策略生命周期，实现自动化Bean发现和注入
 */


@SpringBootApplication
public class StrategyPatternSpringDemo {

    // ==================== 策略接口定义 ====================
    interface PaymentStrategy {
        void processPayment(double amount);
    }

    // ==================== 具体策略实现（Spring Bean）====================
    @Component("wechat") // 通过Bean名称标识策略类型
    static class WechatPayment implements PaymentStrategy {
        @Override
        public void processPayment(double amount) {
            System.out.printf("[Spring] 微信支付成功：%.2f 元%n", amount);
        }
    }

    @Component("alipay") // 策略类型与Bean名称绑定
    static class AlipayPayment implements PaymentStrategy {
        @Override
        public void processPayment(double amount) {
            System.out.printf("[Spring] 支付宝支付成功：%.2f 元%n", amount);
        }
    }

    // ==================== 策略上下文（Spring Bean）====================
    @Component
    static class PaymentContext {
        private final Map<String, PaymentStrategy> strategyMap;

        // Spring自动注入所有PaymentStrategy实现Bean，Key为Bean名称
        @Autowired
        public PaymentContext(Map<String, PaymentStrategy> strategyMap) {
            this.strategyMap = strategyMap;
        }

        // 执行支付策略（非静态方法）
        public void executePayment(String type, double amount) {
            PaymentStrategy strategy = strategyMap.get(type);
            if (strategy == null) {
                throw new IllegalArgumentException("非法的支付类型: " + type);
            }
            strategy.processPayment(amount);
        }
    }

    // ==================== 使用示例 ====================
   public static void  main(String [] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StrategyPatternSpringDemo.class);

        // 获取上下文Bean
        PaymentContext paymentContext = context.getBean(PaymentContext.class);
        
        // 模拟请求参数
        String paymentType = "alipay";
        double amount = 500.0;
        
        // 执行策略调用
        paymentContext.executePayment(paymentType, amount);
        
        // 关闭容器
        context.close();
    }
}

/**
 * Spring实现策略模式的关键要点：
 * 1. Bean名称标识策略类型：使用@Component("type")明确指定策略标识，保持可读性
 * 2. 集合注入特性：Spring会自动将接口所有实现类注入Map<String, PaymentStrategy>
 * 3. 策略发现自动化：新增策略只需添加@Component注解类，无需手动注册
 * 4. 生命周期管理：策略对象由Spring容器管理，天然支持单例/原型作用域
 * 5. 环境集成：可结合@Profile实现环境差异化策略配置
 * 
 * 面试延伸问题：
 * 1. Q: Spring如何实现接口多个实现的自动注入？
 *    A: 通过Map<String, Interface>形式注入，Key为Bean名称，Value为实例对象
 * 
 * 2. Q: 如何控制策略类的加载顺序？
 *    A: 使用@Order注解或实现Ordered接口，但通常策略模式不依赖顺序
 * 
 * 3. Q: 策略类需要读取配置参数如何处理？
 *    A: 使用@Value注入配置，或通过@ConfigurationProperties绑定配置类
 * 
 * 4. Q: 如何实现策略的懒加载？
 *    A: 在策略类上添加@Lazy注解，延迟到首次使用时初始化
 * 
 * 5. Q: 如何避免策略名称冲突？
 *    A: 定义命名规范（如模块前缀），或使用自定义注解标记策略类型
 */