package com.wjx.concurrency;

import com.google.common.collect.Lists;
import com.wjx.util.JsonUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * CompletableFuture 提供了很多对多线程友好的方法，使用它可以很方便地为我们编写多线程程序，什么异步、串行、并行或者等待所有线程执行完任务什么的都非常方便。
 * 注意1 ：CompletableFuture 如果不指定Executor,本质是使用 ForkJoinPool.commonPool() 来提供池化技术.
 * 所以一般可用于计算密集型业务。如果一个业务提交很多IO密集型业务导致阻塞，可能影响其他业务的执行
 *
 * @Author wangjiaxing
 * @Date 2023/1/22
 */
public class TCompleteFuture {
    public static AtomicInteger taskNum = new AtomicInteger(0);

    public static void main(String[] args) {
//        timeOutTest();
//        multiThreadTest();
//        blockOtherTaskTest();
        testMultiThreadException();
    }

    private static void blockOtherTaskTest() {
        //主任务一直执行
        CompletableFuture<Void> mainTask = CompletableFuture.runAsync(() -> {
                    while (true) {
                        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                            taskNum.incrementAndGet();
                            System.out.println(String.format("main task %s start", taskNum.get()));
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(String.format("main task %s end", taskNum.get()));
                            return String.format("main task %s end", taskNum.get());
                        });
                    }
                }
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    System.out.println(String.format("blocking task %s start", taskNum.get()));
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("blocking task %s end", taskNum.get()));
                }
            });
        }

        mainTask.join();
    }

    private static void multiThreadTest() {
        CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
            return "tesk 1";
        });
        CompletableFuture future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "tesk 2";
        });
        CompletableFuture<Void> futures = CompletableFuture.allOf(future1, future2);
        //等待结果完成/抛出异常（有子结果抛出异常）
        futures.join();
        System.out.println("all done: " + futures.isDone());
    }


    static void testGetTimeOutTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1";
        });

        String result = null;
        try {
            result = stringCompletableFuture.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("time out is occur");
        }
    }


    static void testMultiThreadException() {
        List<Integer> collect = Lists.newArrayList(1, 2, 3, 4, 5).stream().map(task -> CompletableFuture.supplyAsync(() -> {
            if (task == 2) {
                throw new RuntimeException();
            }
            return task;
        })).map(c -> c.join()).collect(Collectors.toList());
        System.out.println(JsonUtil.toString(collect));
    }

}
