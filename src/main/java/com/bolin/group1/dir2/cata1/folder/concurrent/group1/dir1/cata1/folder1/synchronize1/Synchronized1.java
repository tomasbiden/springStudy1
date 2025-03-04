package com.bolin.group1.dir2.cata1.folder.concurrent.group1.dir1.cata1.folder1.synchronize1;

public class Synchronized1 {
    // 共享的锁对象，用于线程同步
    private static final Object lock = new Object();
    // 计数器，记录当前需要打印的数字
    private static int counter = 1;

    private static volatile int volatileCounter1 = 1;
    // 状态标志，表示是否轮到线程1执行
    private static boolean isThread1Turn = true;
    public static void printOneToHundrend(){


        // 线程1：负责在 isThread1Turn 为 true 时打印
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    // 如果数字超过100，终止线程
                    if (counter > 100) {
                        lock.notifyAll(); // 唤醒线程2以防其阻塞
                        break;
                    }
                    // 只有轮到线程1且数字未超限时才打印
                    if (isThread1Turn) {
                        System.out.println(Thread.currentThread().getName() + ": " + counter);
                        counter++;
                        isThread1Turn = false; // 切换为线程2的回合
                        lock.notify();         // 唤醒线程2
                    } else {
                        try {
                            lock.wait(); // 未轮到自己，释放锁并等待
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Thread-1");

        // 线程2：负责在 isThread1Turn 为 false 时打印
        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (counter > 100) {
                        lock.notifyAll(); // 唤醒线程1以防其阻塞
                        break;
                    }
                    if (!isThread1Turn) {
                        System.out.println(Thread.currentThread().getName() + ": " + counter);
                        counter++;
                        isThread1Turn = true; // 切换为线程1的回合
                        lock.notify();        // 唤醒线程1
                    } else {
                        try {
                            lock.wait(); // 未轮到自己，释放锁并等待
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "Thread-2");

        // 启动线程
        thread1.start();
        thread2.start();
    }


    public static void printOneToHundrendWithoutLock(){


        // 线程1：负责在 isThread1Turn 为 true 时打印
        Thread thread1 = new Thread(() -> {
            while (true) {

                    if (isThread1Turn&&volatileCounter1<=100) {
                        System.out.println(Thread.currentThread().getName() + ": " + volatileCounter1);
                        volatileCounter1++;
                        isThread1Turn = false; // 切换为线程2的回合
//                        lock.notify();         // 唤醒线程2
                    }

            }
        }, "Thread-1");

        // 线程2：负责在 isThread1Turn 为 false 时打印
        Thread thread2 = new Thread(() -> {
            while (true) {

                    if (!isThread1Turn&&volatileCounter1<=100) {
                        System.out.println(Thread.currentThread().getName() + ": " + volatileCounter1);
                        volatileCounter1++;
                        isThread1Turn = true; // 切换为线程1的回合
//                        lock.notify();        // 唤醒线程1
                    }

            }
        }, "Thread-2");

        // 启动线程
        thread1.start();
        thread2.start();
    }

    public  static void main(String[] args){
//        printOneToHundrend();
        printOneToHundrendWithoutLock();
    }
}
