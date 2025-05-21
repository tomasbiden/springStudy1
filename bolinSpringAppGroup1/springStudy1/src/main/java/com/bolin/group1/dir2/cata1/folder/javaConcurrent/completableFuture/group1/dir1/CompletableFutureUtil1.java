package com.bolin.group1.dir2.cata1.folder.javaConcurrent.completableFuture.group1.dir1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureUtil1 {
    /**
     * CompletableFuture核心应用场景：
     * 1. 异步任务编排：当需要并行执行多个独立任务并合并结果时（如电商系统调用风控+库存服务）
     * 2. 回调式处理：异步操作完成后自动触发后续动作（如支付成功后通知物流系统）
     * 3. 复杂流水线：创建有依赖关系的任务链（先查询用户信息再获取订单列表）
     * 4. 超时控制：给异步操作添加超时fallback机制
     * 5. 异常恢复：在异步链路中优雅处理服务调用异常
     *
     * 典型使用案例：
     * - 聚合多个微服务调用结果（商品详情页聚合商品信息、库存、评论）
     * - 高并发场景下的异步非阻塞处理（秒杀系统队列处理）
     * - 定时任务批处理（并行执行数据清洗任务）
     */

    /**
     * 基础用法：异步执行+结果转换
     */
    private static void basicUsageDemo() throws ExecutionException, InterruptedException {
        // 异步执行任务并指定线程池（实际开发中建议自定义线程池）
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("【基础任务】执行线程: " + Thread.currentThread().getName());
            return "原始结果";
        });

        // 添加回调（同步执行，使用thenApply转换结果）
        CompletableFuture<String> transformed = future.thenApply(result -> {
            System.out.println("【转换任务】执行线程: " + Thread.currentThread().getName());
            return result + " -> 转换后";
        });

        System.out.println("最终结果: " + transformed.get());
    }

    /**
     * 组合任务：异步任务A的结果作为任务B的输入
     */
    private static void combinationDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<String> queryUser = CompletableFuture.supplyAsync(() -> {
            sleep(1); // 模拟IO延迟
            return "用户ID_1001";
        });

        // thenCompose实现任务链式调用
        CompletableFuture<String> orderTask = queryUser.thenCompose(userId ->
                CompletableFuture.supplyAsync(() -> {
                    sleep(1);
                    return userId + "的订单列表";
                })
        );

        System.out.println("组合任务结果: " + orderTask.get());
    }

    /**
     * 异常处理：捕获并恢复异步链中的异常
     */
    private static void exceptionHandleDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> riskyTask = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("模拟业务异常");
            }
            return 42;
        });

        // 使用exceptionally进行异常恢复
        CompletableFuture<Integer> safeTask = riskyTask.exceptionally(ex -> {
            System.out.println("捕获异常: " + ex.getMessage());
            return 0; // 返回默认值
        });

        System.out.println("异常处理结果: " + safeTask.get());
    }

    /**
     * 超时控制：防止异步任务长时间阻塞
     */
    private static void timeoutControlDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<String> slowTask = CompletableFuture.supplyAsync(() -> {
            sleep(3); // 该任务需要2秒完成
            return "正常结果";
        });

        // 设置1秒超时并返回默认值
        CompletableFuture<String> timeoutTask = slowTask
                .completeOnTimeout("超时默认值", 1, TimeUnit.SECONDS);

        System.out.println("超时控制结果: " + timeoutTask.get());
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws Exception {
        basicUsageDemo();
        combinationDemo();
        exceptionHandleDemo();
        timeoutControlDemo();
    }
}
