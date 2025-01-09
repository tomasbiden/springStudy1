package com.bolin.group1.deadLock;

public class DeadlockExample {

    // 定义两个资源
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        /*
        // 创建两个线程
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) { // 获取资源1的锁
                System.out.println(Thread.currentThread().getName() + " locked resource1");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource2) { // 获取资源2的锁
                    System.out.println(Thread.currentThread().getName() + " locked resource2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) { // 获取资源2的锁
                System.out.println(Thread.currentThread().getName() + " locked resource2");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource1) { // 获取资源1的锁
                    System.out.println(Thread.currentThread().getName() + " locked resource1");
                }
            }
        });

        // 启动两个线程
        thread1.start();
//        thread2.start();
        Thread.sleep(100000);

         */
//        死锁测试
        int h=1;
    }
}
