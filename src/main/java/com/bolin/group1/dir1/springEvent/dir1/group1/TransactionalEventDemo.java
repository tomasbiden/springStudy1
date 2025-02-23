package com.bolin.group1.dir1.springEvent.dir1.group1;

import com.bolin.Application;
import com.bolin.controller.UserAnswerController;
import com.bolin.group2.dir1.cata1.demos.pojo.UserAnswer;
import com.bolin.mapper.UserAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;

/**
 * @TransactionalEventListener 知识体系详解
 * 
 * 核心作用：在事务生命周期特定阶段触发事件处理，确保事件处理与事务状态同步
 * 
 * 典型应用场景：
 * 1. 事务成功后发送消息通知（如邮件、短信）
 * 2. 审计日志记录（仅当事务成功提交时记录）
 * 3. 数据同步（确保主事务成功后再同步到其他系统）
 * 4. 补偿操作（事务回滚时触发补偿逻辑）
 * 5. 缓存更新（事务提交后刷新缓存保证一致性）
 */
@SpringBootApplication
public class TransactionalEventDemo {

    // 1. 定义领域事件
    public static class OrderCreatedEvent {
        private final String orderId;
        public OrderCreatedEvent(String orderId) {
            this.orderId = orderId;
        }
        public String getOrderId() { return orderId; }
    }

    // 2. 业务服务（事件发布者）
    @Service
    public static class OrderService {
        @Autowired
        private ApplicationEventPublisher eventPublisher;

        @Transactional
        public void createOrder(String orderId) {
            // 业务逻辑...
            System.out.println("保存订单到数据库: " + orderId);
            
            // 发布领域事件
            eventPublisher.publishEvent(new OrderCreatedEvent(orderId));
        }
    }



    // 4. 测试入口
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        OrderService service = context.getBean(OrderService.class);
        UserAnswerController userAnswerController = context.getBean(UserAnswerController.class);
        UserAnswerMapper userAnswerMapper = context.getBean(UserAnswerMapper.class);

        // 测试正常提交
        try {
            service.createOrder("1001");
        } catch (Exception e) {
            System.out.println("业务异常: " + e.getMessage());
        }

        // 测试回滚场景
        try {
            service.createOrder("1002");
//            throw new RuntimeException("模拟业务异常");
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setId(1L);
            userAnswer.setAppId(2L);
            userAnswer.setUserId(2L);
            userAnswerMapper.insert(userAnswer);


        } catch (Exception e) {
            System.out.println("业务异常: " + e.getMessage());
        }
    }
}

/**
 * 关键注意点：
 * 1. 事务绑定：监听器默认绑定到发布事件的事务，需确保事件发布在事务上下文中
 * 2. 阶段选择：明确需要处理的交易阶段（AFTER_COMMIT/AFTER_ROLLBACK等）
 * 3. 异步处理：需要配合@Async实现异步时，要确保事务上下文传播
 * 4. 异常处理：监听器中抛出异常会影响主事务（需结合@Async和异常处理器）
 * 5. 条件过滤：可结合condition参数实现条件化事件处理
 */

/**
 * 常见面试问题与回答：
 * 
 * Q1: @TransactionalEventListener和@EventListener的区别是什么？
 * A1: 前者与事务生命周期绑定，后者与事件发布立即执行。前者可以指定事务阶段触发。
 * 
 * Q2: TransactionPhase有哪些可选值？默认是什么？
 * A2: AFTER_COMMIT（默认）、AFTER_ROLLBACK、AFTER_COMPLETION、BEFORE_COMMIT
 * 
 * Q3: 事务未启用时使用@TransactionalEventListener会怎样？
 * A3: 事件会立即执行，等同于@EventListener，会打印警告日志
 * 
 * Q4: 如何实现监听器的异步处理？
 * A4: 结合@Async注解，并配置异步任务执行器，注意事务传播设置
 * 
 * Q5: 监听器中抛出异常会影响主事务吗？
 * A5: 在默认同步模式下会影响，异步模式下不会
 * 
 * Q6: BEFORE_COMMIT阶段的使用场景是什么？
 * A6: 需要在事务提交前进行最终验证或预提交操作
 * 
 * Q7: 如何实现条件化的事件处理？
 * A7: 使用condition参数结合SpEL表达式，如@TransactionalEventListener(condition = "#event.orderId.startsWith('VIP')")
 * 
 * Q8: 事务事件监听器可以参与原有事务吗？
 * A8: 默认不参与，需要设置@Transactional(propagation = Propagation.REQUIRES_NEW)
 * 
 * Q9: 多个监听器的执行顺序如何控制？
 * A9: 使用@Order注解或实现Ordered接口
 * 
 * Q10: 在分布式事务场景下如何保证可靠性？
 * A10: 需要结合事务消息中间件实现最终一致性，或使用事务日志+补偿机制
 */
