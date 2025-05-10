package com.bolin.group1.dir1.springEvent.dir1.group1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;

/**
 * Spring 事件机制深度解析
 *
 * 核心价值：通过观察者模式实现组件间松耦合通信，适用于需要解耦业务逻辑的场景
 *
 * 典型应用场景：
 * 1. 业务操作后的异步通知（如订单创建后发送短信）
 * 2. 系统状态变更的日志记录（如配置修改审计）
 * 3. 缓存同步（数据更新后刷新分布式缓存）
 * 4. 业务流程的扩展点（通过事件触发额外处理）
 * 5. 事务边界操作（结合@TransactionalEventListener）
 */
@SpringBootApplication
@EnableAsync
public class EventDemoApplication {


    // 1. 自定义线程池配置
    @Bean(name = "customEventPool")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);    // 核心线程数
        executor.setMaxPoolSize(5);     // 最大线程数
        executor.setQueueCapacity(100); // 队列容量
        executor.setThreadNamePrefix("CustomEvent-"); // 线程名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
        executor.initialize();
        return executor;
    }

    // 1. 定义自定义事件
    public  class OrderCreateEvent extends ApplicationEvent {
        private final String orderId;
        public OrderCreateEvent(Object source, String orderId) {
            super(source);
            this.orderId = orderId;
        }
        public String getOrderId() { return orderId; }
    }

    // 2. 事件发布者服务
    @Service
    public  class OrderService {
        @Autowired
        private ApplicationEventPublisher publisher;



        public void createOrder(String orderId) {
            System.out.println("线程"+Thread.currentThread().getId()+"创建订单："+orderId );
            OrderCreateEvent event = new OrderCreateEvent(this, orderId);
            publisher.publishEvent(event);
            System.out.println("线程"+Thread.currentThread().getId()+"创建订单发布事件后的下一步");
        }
    }

    // 3. 事件监听器（注解方式）
    @Component
    public  class LogListener {
        @EventListener
//        @Async
        public void logOrderCreate(OrderCreateEvent event) throws InterruptedException {
            Thread.sleep(1000);
            System.out.println("线程"+Thread.currentThread().getId()+"【日志】记录订单创建：" + event.getOrderId());

        }
    }

    // 2. 修改监听器（移除@Async注解）
    @Component
    public class LogListener2 {
        @Autowired
        @Qualifier("customEventPool") // 注入自定义线程池
        private Executor taskExecutor;

//        @EventListener
        public void logOrderCreate(OrderCreateEvent event) {
            // 将任务提交到线程池
            taskExecutor.execute(() -> {
                System.out.println("线程"+Thread.currentThread().getId()+"【日志】记录订单创建：" + event.getOrderId());
                // 模拟耗时操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程"+Thread.currentThread().getId()+"【日志】处理完成");
            });
        }
    }

    /**
     * 监听器1：库存管理系统
     */
    @Component
    public  class InventoryListener {
        @Autowired
        @Qualifier("customEventPool")
        private Executor taskExecutor;

        @EventListener
        public void handleOrderCreate(OrderCreateEvent event) {
            taskExecutor.execute(() -> {
                System.out.println("线程"+Thread.currentThread().getId()+"【库存】开始锁定库存: "+event.getOrderId());
                try {
                    // 模拟库存查询和锁定（耗时800ms）
                    Thread.sleep(800);
                    System.out.println("线程"+Thread.currentThread().getId()+"【库存】已锁定: "+event.getOrderId());
                } catch (InterruptedException e) {
                    System.err.println("库存处理中断: "+e.getMessage());
                }
            });
        }
    }

    /**
     * 监听器2：积分系统
     */
    @Component
    public class PointsListener {
        @Autowired
        @Qualifier("customEventPool")
        private Executor taskExecutor;

        @EventListener
        public void addUserPoints(OrderCreateEvent event) {
            taskExecutor.execute(() -> {
                System.out.println("线程"+Thread.currentThread().getId()+"【积分】开始计算积分: "+event.getOrderId());
                try {
                    // 模拟积分计算（耗时500ms）
                    Thread.sleep(500);
                    System.out.println("线程"+Thread.currentThread().getId()+"【积分】积分已添加: "+event.getOrderId());
                } catch (InterruptedException e) {
                    System.err.println("积分处理中断: "+e.getMessage());
                }
            });
        }
    }

    /**
     * 监听器3：通知服务（短信+邮件）
     */
    @Component
    public  class NotificationListener {
        @Autowired
        @Qualifier("customEventPool")
        private Executor taskExecutor;

        // 只监听OrderCreateEvent类型（两种等效写法）
//     ,condition = "#event.orderId != null "   && event.orderId == '202308150001'
//        @EventListener(classes = OrderCreateEvent.class)

//          @EventListener(value = OrderCreateEvent.class)
//        @EventListener(condition = "#root.args[0].orderId =='202308150001'")
//        去掉#就一切正常了
        @EventListener(condition = "#root.event().orderId == '202308150001'")

        public void sendNotifications(OrderCreateEvent event) {
            ExpressionParser expressionParser = new SpelExpressionParser();
              Expression expression1 = expressionParser.parseExpression("#root");
            Expression expression = expressionParser.parseExpression("#root.args[0]");
            taskExecutor.execute(() -> {
                System.out.println("线程"+Thread.currentThread().getId()+"【通知】准备发送通知: "+event.getOrderId());
                try {
                    // 并行发送两种通知
                    CompletableFuture.allOf(
                            CompletableFuture.runAsync(() -> {
                                System.out.println("线程"+Thread.currentThread().getId()+"【短信】发送成功: "+event.getOrderId());
                            }, taskExecutor),
                            CompletableFuture.runAsync(() -> {
                                System.out.println("线程"+Thread.currentThread().getId()+"【邮件】发送成功: "+event.getOrderId());
                            }, taskExecutor)
                    ).get();
                } catch (Exception e) {
                    System.err.println("通知发送失败: "+e.getMessage());
                }
            });
        }
    }

    // 4. 主启动类
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(EventDemoApplication.class, args);
        OrderService service = context.getBean(OrderService.class);

        // 创建并发测试线程池（与事件处理线程池分开）
        ExecutorService concurrentExecutor = Executors.newFixedThreadPool(10);

        // 模拟20个并发请求
        int requestCount = 1;
        CountDownLatch latch = new CountDownLatch(requestCount);

        System.out.println("===== 开始并发测试 =====");

        for (int i = 0; i <requestCount; i++) {
            final String orderId = "20230815" + String.format("%04d", i+1);
            concurrentExecutor.execute(() -> {
                try {
                    service.createOrder(orderId);
                } finally {
                    latch.countDown();
                }
            });
        }

        // 等待所有请求完成
        latch.await(30, TimeUnit.SECONDS);

        // 关闭资源
        concurrentExecutor.shutdown();
        context.close();

        System.out.println("===== 并发测试完成 =====");
    }
    /**
     * 常见面试问题与回答：
     *
     * Q1: Spring事件机制的核心组件有哪些？
     * A1: ApplicationEvent(事件)、ApplicationListener(监听器)、ApplicationEventPublisher(发布者)
     *
     * Q2: @EventListener和实现ApplicationListener接口的区别？
     * A2: 注解方式更灵活，支持条件过滤和异步处理；接口方式适合统一处理多种事件
     *
     * Q3: 如何实现监听器的异步执行？
     * A3: 配合@Async注解，并配置线程池：@EnableAsync + TaskExecutor
     *
     * Q4: 多个监听器的执行顺序如何控制？
     * A4: 使用@Order注解或实现Ordered接口，数值越小优先级越高
     *
     * Q5: 事务事件监听器(@TransactionalEventListener)的各个阶段区别？
     * A5: BEFORE_COMMIT(提交前)、AFTER_COMMIT(提交后)、AFTER_ROLLBACK(回滚后)、AFTER_COMPLETION(完成时)
     *
     * Q6: 如何防止事件传播导致的循环触发？(不懂)
     * A6: (1)检查事件来源 (2)设置状态标识 (3)使用专用事件类型
     *
     * Q7: 事件机制在分布式系统中的应用限制？
     * A7: 本地事件只在单个JVM有效，分布式场景需要集成消息队列
     *
     * Q8: 如何实现条件化的事件处理？
     * A8: 使用condition参数配合SpEL表达式，如@EventListener(condition = "#root.event().amount > 100")
     *
     * Q9: 事件发布后如果没有监听器会怎样？
     * A9: 不会有任何操作，事件会被静默忽略
     *
     * Q10: 如何保证事件处理的事务边界？
     * A10: (1)监听器方法添加@Transactional (2)使用@TransactionalEventListener控制执行阶段
     */
}