package com.wjx.java.concurrency.coreclass;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Semaphore 信号量
 * @Author wangjiaxing
 * @Date 2023/1/23
 */
public class TSemaphore {
    private static AtomicInteger taskNo = new AtomicInteger(0);


    public static void main(String[] args) {
        doTask();
    }
    /**
     * 实现 最多只有五个线程执行
     */
    static void doTask() {
        Semaphore semaphore = new Semaphore(5);
        while (true) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    int i = taskNo.incrementAndGet();
                    System.out.println(String.format("task %s is execute", i));
                    Thread.sleep(1000);
                    System.out.println(String.format("task %s is down", i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }).start();
        }
    }
}
