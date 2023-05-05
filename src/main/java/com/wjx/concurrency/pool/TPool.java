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

//        CREATE TABLE `order` (
//          `id` varchar(100) NOT NULL,
//          `user_id` varchar(100) NOT NULL,
//          `create_time` datetime DEFAULT NULL,
//          `update_time` datetime DEFAULT NULL,
//          `disburse_timestamp` bigint(20) DEFAULT NULL,
//          `create_timestamp` bigint(20) DEFAULT NULL,
//          `update_timestamp` bigint(20) DEFAULT NULL
//        PRIMARY KEY (`id`),
//        KEY `idx_user_id` (`user_id`),
//        KEY `idx_create_timestamp` (`create_timestamp`),
//        KEY `idx_disburse_timestamp` (`disburse_timestamp`),
//        KEY `idx_update_timestamp` (`update_timestamp`)
//        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 ROW_FORMAT = DYNAMIC COMMENT = '订单表'



//        1.原始sql
//        "SELECT `id`,`disburse_time` FROM `order` WHERE disburse_timestamp < #{tagTime} order by `disburse_timestamp` desc limit #{limitNum} offset #{offsetNum}"
//        性能不足点分析：
//          1,没有使用覆盖索引,导致回表： select 的列 `disburse_time`, id` 和 order by 的`disburse_timestamp` 没在一个索引，不能使用覆盖索引，select中的disburse_time改为`disburse_timestamp`,从而使用覆盖索引
//          2.如果数据很多：
//              offset过大影响性能的原因是。offset是先查出where的结果(多次通过主键索引访问数据块的I/O操作，导致了大量的I/O)再offset，（注意，只有InnoDB有这个问题，而MYISAM索引结构与InnoDB不同，二级索引都是直接指向数据块的，因此没有此问题 ）。
//              offset解决方案: 因此我们先查出偏移后的主键，再根据主键索引查询数据块的所有内容(此时只有limitNum的数据I/O)即可优化。
//        sql优化：
//        查部分数据： SELECT `id`,`disburse_timestamp` FROM `order` WHERE disburse_timestamp >= #{lastDisburseTimestamp} and disburse_timestamp < #{tagTimeStamp} order by `disburse_timestamp` limit #{limitNum}
//        查所有数据:  SELECT * from `order` where id in (select id from `order` where disburse_timestamp < #{tagTime} order by `disburse_timestamp` limit offsetNum,limitNum )


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
