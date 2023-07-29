package com.wjx.java.concurrency.coreclass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 内部通过一个 count 变量作为计数器，count 的初始值为 parties 属性的初始化值，
 * 每当一个线程到了栅栏这里了(await)，阻塞且将计数器减 1。如果 count 值为 0 了，即所有线程到达栅栏：
 *      1。执行我们构造方法中输入的任务，
 *      2。然后，唤醒所有阻塞线程继续执行任务
 * @Author wangjiaxing
 * @Date 2023/1/22
 */
public class TCyclicBarrier {
    public static void main(String[] args) {
        testCyclicBarrier();
    }

    private static void testCyclicBarrier() {
        CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("all thread is awaited");

            }
        });
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI + " in");
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI + " out");
                }
            }).start();
        }
        System.out.println("main down");
    }
}
