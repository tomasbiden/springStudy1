package com.bolin.group1.dir1.springEvent.dir1.group1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Spring事件机制应用场景与案例：
 * 1. 业务解耦：用户注册成功后发送邮件、短信、初始化积分等操作
 * 2. 状态同步：商品库存变化时同步更新缓存和搜索索引
 * 3. 操作日志：关键业务操作后记录审计日志
 * 4. 事务完成后操作：数据库事务提交后发送通知
 * 5. 系统监控：异常发生时触发告警通知
 * 典型案例：电商订单状态变更后触发物流通知、库存扣减、积分计算等
 */
@SpringBootApplication
public class SpringEventDemo {

    // 自定义事件类需要继承ApplicationEvent
    public static class UserRegisterEvent extends ApplicationEvent {
        private final String username;
        
        public UserRegisterEvent(Object source, String username) {
            super(source);
            this.username = username; // 事件携带业务数据
        }
        public String getUsername() { return username; }
    }

    @Service
    public static class UserService {
        @Autowired
        private ApplicationEventPublisher eventPublisher; // 事件发布器

        public void registerUser(String username) {
            // 模拟用户注册逻辑
            System.out.println("注册用户：" + username);
            
            // 发布自定义事件
            eventPublisher.publishEvent(new UserRegisterEvent(this, username));
        }
    }

    // 事件监听方式1：使用@EventListener注解
    @Component
    public static class EmailListener {
        @EventListener
        @Order(1) // 设置监听器执行顺序
        public void sendWelcomeEmail(UserRegisterEvent event) {
            // 模拟发送邮件
            System.out.println("【邮件】发送欢迎邮件给：" + event.getUsername());
        }
    }

    // 事件监听方式2：实现ApplicationListener接口
    @Component
    public static class LogListener implements ApplicationListener<UserRegisterEvent> {
        @Override
        @Async // 异步处理需要配合@EnableAsync
        public void onApplicationEvent(UserRegisterEvent event) {
            // 模拟记录日志
            System.out.println("【日志】记录用户注册日志：" + event.getUsername());
        }
    }



        public static void main(String[] args) {
            ConfigurableApplicationContext context = SpringApplication.run(SpringEventDemo.class, args);
            UserService userService = context.getBean(UserService.class);
            userService.registerUser("张伟"); // 触发事件
        }

}

/**
 * 5个重要注意事项：
 * 1. 默认同步执行：事件监听与主线程同步，需要异步需配合@Async和@EnableAsync
 * 2. 事件对象不可变：事件发布后不应修改其状态，保证线程安全
 * 3. 监听器顺序：使用@Order控制同步监听器的执行顺序，异步场景无效
 * 4. 异常处理：监听器异常会传播到事件发布者，需要做好异常捕获
 * 5. 性能影响：避免在核心链路过度使用，特别是同步监听器可能影响性能
 * 
 * 10个常见面试问题与回答：
 * 1. Q: Spring事件机制基于什么设计模式？
 *    A: 观察者模式（发布-订阅模式）
 * 
 * 2. Q: 如何自定义一个事件？
 *    A: 继承ApplicationEvent类，添加业务字段，通过ApplicationEventPublisher发布
 * 
 * 3. Q: 同步和异步事件处理的区别？
 *    A: 默认同步在主线程执行，异步需要@Async和@EnableAsync，使用线程池处理
 * 
 * 4. Q: 如何保证多个监听器的执行顺序？
 *    A: 使用@Order注解，数值越小优先级越高，仅对同步监听器有效
 * 
 * 5. Q: 事件处理抛出异常会怎样？
 *    A: 同步监听器异常会传播到发布者，可能影响主流程，需做异常处理
 * 
 * 6. Q: 如何实现全局事件监听？
 *    A: 定义监听器时使用泛型通配符ApplicationEvent，或监听具体父类事件
 * 
 * 7. Q: 事件机制在集群环境如何工作？
 *    A: 默认只在单个JVM内有效，需要分布式事件需集成消息中间件
 * 
 * 8. Q: 与MQ的区别是什么？
 *    A: Spring事件是进程内通信，MQ用于跨进程/系统通信，两者可结合使用
 * 
 * 9. Q: @EventListener和实现接口方式有什么区别？
 *    A: @EventListener更灵活，支持条件过滤，接口方式需要实现特定方法
 * 
 * 10. Q: 如何监听多种类型事件？
 *     A: 在@EventListener方法参数使用父类类型，或定义多个监听方法
 */