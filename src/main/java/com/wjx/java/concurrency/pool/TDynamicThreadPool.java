package com.wjx.java.concurrency.pool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池动态参数设置和监控
 * @Author wangjiaxing
 * @Date 2023/1/27
 */
public class TDynamicThreadPool extends TPool {

    public static void main(String[] args) {
        dynamicThreadBuild();
    }

    private static void dynamicThreadBuild() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(getThreadSize(TaskType.IO_INTENSIVE), getThreadSize(TaskType.IO_INTENSIVE), 10, TimeUnit.SECONDS, new ResizeableCapacityLinkedBlockingQueue<Runnable>(10), getNameThreadFactory("T-Dynamic-", false));

        for (int i = 0; i < 15; i++) {
            poolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPoolStatus(poolExecutor, "改变之前");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.setCorePoolSize(14);
        poolExecutor.setMaximumPoolSize(16);

        poolExecutor.prestartAllCoreThreads();
        ((ResizeableCapacityLinkedBlockingQueue)poolExecutor.getQueue()).setCapacity(100);
        threadPoolStatus(poolExecutor, "改变之后");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void threadPoolStatus(ThreadPoolExecutor pool, String name) {
        BlockingQueue<Runnable> queue = pool.getQueue();
        System.out.println(String.format("%s - %s - 核心线程数: %s, 活动线程数: %s, " +
                        "最大线程数: %s, 线程活跃度: %s , 任务完成数: %s ,队列大小: %s , 当前排队线程数: %s ," +
                        " 队列剩余大小: %s, 队列使用度: %s ",
                Thread.currentThread().getName(), name, pool.getCorePoolSize(), pool.getActiveCount(),
                pool.getMaximumPoolSize(), divide(pool.getActiveCount(), pool.getMaximumPoolSize()), pool.getCompletedTaskCount(), queue.size() + queue.remainingCapacity(), queue.size(),
                queue.remainingCapacity(), divide(queue.size(), queue.size() + queue.remainingCapacity())));
    }


    public static String divide(int d1, int d2) {
        return new BigDecimal(d1*100).divide(new BigDecimal(d2),2,RoundingMode.UP).toString() + "%";
    }



}
