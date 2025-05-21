package com.bolin.group1.dir1.springEvent.dir1.group1;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

// 3. 事务事件监听器
    @Component
    public  class TransactionalEventListener1 {
        /**
         * AFTER_COMMIT（默认）：事务成功提交后执行
         */
        @TransactionalEventListener
        public void handleCommit(TransactionalEventDemo.OrderCreatedEvent event) {
            System.out.println("【事务提交】发送订单通知: " + event.getOrderId());
        }

        /**
         * AFTER_ROLLBACK：事务回滚后执行
         */
        @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
        public void handleRollback(TransactionalEventDemo.OrderCreatedEvent event) {
            System.out.println("【事务回滚】执行补偿操作: " + event.getOrderId());
        }

        /**
         * AFTER_COMPLETION：无论提交或回滚都会执行
         */
        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
        public void handleCompletion(TransactionalEventDemo.OrderCreatedEvent event) {
            System.out.println("【事务完成】清理资源: " + event.getOrderId());
        }
    }