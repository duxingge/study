package com.wjx.concurrency.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 线程池基本使用用法：
 * 1.通过构造方法：new ThreadPoolExecutor()
 * 2.通过工具类Executors
 * 3.通过CompleteFuture等使用ForkJoinPool.commonPool()
 * @Author wangjiaxing
 * @Date 2023/1/22
 */
public abstract class TPool {
    private ThreadPoolExecutor synPoolExecutor = new ThreadPoolExecutor(getThreadSize(TaskType.IO_INTENSIVE), getThreadSize(TaskType.IO_INTENSIVE), 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(500), getNameThreadFactory("syn-contract",false), new ThreadPoolExecutor.CallerRunsPolicy());


    private volatile boolean end = true;

    private volatile boolean cancel = false;

    private volatile int batch = 1;
    List<String> agreementList = new ArrayList<>();

    // 优化1： 计算密集型任务可以使用 CompletableFuture 类来改进！Java8 的 CompletableFuture 提供了很多对多线程友好的方法，使用它可以很方便地为我们编写多线程程序，什么异步、串行、并行或者等待所有线程执行完任务什么的都非常方便。
    public synchronized void doTask() {
        //同步数据
        while (!end) {
            initBatchData();
            CountDownLatch latch = new CountDownLatch(agreementList.size());
            agreementList.stream().forEach( it->{
                String agree = it;
                synPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doItem();
                        } finally {
                            latch.countDown();
                        }
                    }
                });
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cancel) {
                end = true;
            }
        }
        System.out.println("task down");
    }


    public synchronized void doTask2() {
        //同步数据
        while (!end) {
            initBatchData();
            List<CompletableFuture<Void>> futures = agreementList.stream().map(it -> CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    doItem();
                }
            })).collect(Collectors.toList());
            CompletableFuture<Void> resultFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
            resultFuture.join();
            if (cancel) {
                end = true;
            }
        }
        System.out.println("task down");
    }





    private void doItem() {
    }

    private void initBatchData() {
        batch++;
        //init batch data
    }

    // named for thread with ThreadFactory
    public static ThreadFactory getNameThreadFactory(String threadNamePrefix, boolean daemon) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadNamePrefix + "-%d")
                .setDaemon(daemon)
                .build();
        return threadFactory;
    }

    /**
     * 获取线程池线程数：
     *  线程池里的任务基本分为计算密集型和IO密集型任务。
     *  1.计算密集型任务:CPU内核数+1 (之所以加1是因为密集型任务可能会因为缺页或其他原因导致中断，+1可以更好的利用CPU)
     *  2.IO密集型任务: 2*CPU内核数
     * @return
     */
    public static int getThreadSize(TaskType taskType) {
        switch (taskType) {
            case IO_INTENSIVE:
                return 2 * Runtime.getRuntime().availableProcessors();
            case CALCULATE_INTENSIVE:
                return Runtime.getRuntime().availableProcessors() + 1;
        }
        return Runtime.getRuntime().availableProcessors() + 1;
    }

    enum TaskType {
        CALCULATE_INTENSIVE,
        IO_INTENSIVE
    }
}
