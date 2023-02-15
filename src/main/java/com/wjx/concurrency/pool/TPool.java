package com.wjx.concurrency.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 线程池使用：
 * 1.通过构造方法：new ThreadPoolExecutor()
 * 2.通过工具类Executors
 * 3.通过CompleteFuture等使用ForkJoinPool.commonPool()
 * @Author wangjiaxing
 * @Date 2023/1/22
 */
public abstract class TPool {
    private ThreadPoolExecutor synPoolExecutor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(500), getNameThreadFactory("syn-contract",false), new ThreadPoolExecutor.CallerRunsPolicy());


    private volatile boolean end = true;

    private volatile boolean cancel = false;

    private volatile int batch = 1;
    List<String> agreementList = new ArrayList<>();

    // 优化1： 可以使用 CompletableFuture 类来改进！Java8 的 CompletableFuture 提供了很多对多线程友好的方法，使用它可以很方便地为我们编写多线程程序，什么异步、串行、并行或者等待所有线程执行完任务什么的都非常方便。
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
}
