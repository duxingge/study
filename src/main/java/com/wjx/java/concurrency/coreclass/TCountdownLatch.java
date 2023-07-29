package com.wjx.java.concurrency.coreclass;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch 是共享锁的一种实现,它默认构造 AQS 的 state 值为 count。
 * 当线程使用 countDown() 方法时,其实使用了tryReleaseShared方法以 CAS 的操作来减少 state,直至 state 为 0 。
 * 当调用 await() 方法的时候，如果 state 不为 0，那就证明任务还没有执行完毕，await() 方法就会一直阻塞，也就是说 await() 方法之后的语句不会被执行。
 * 然后，CountDownLatch 会自旋 CAS 判断 state == 0，如果 state == 0 的话，就会释放所有等待的线程，await() 方法之后的语句得到执行。
 *
 *
 * 1. AQS: 当线程请求共享资源时，如果共享资源空闲，则设置当前线程为工作线程，并将共享资源设为锁定；
 * 如果共享资源锁定，需要提供当前线程阻塞且被唤醒时锁分配的机制，这个机制AQS是通过CLH(双向队列)实现的。
 * 1.1 每一个节点代表一个线程，保存着节点在队列中的状态，线程引用，上一节点，下一节点。
 * 1.2 线程的阻塞和唤醒机制是通过Unsafe.park和unpark实现的，当有资源release时，会unpark首节点线程(headNode)
 * 1.3 使用volatile int status 代表同步状态和资源状态，保证可见性
 *
 * 2. CountDownLatch的计算密集型任务可以由CompletedFuture来替换，见{@link com.wjx.java.concurrency.pool.TPool}
 *
 * 3. CountDownLatch
 *
 * @Author wangjiaxing
 * @Date 2023/1/23
 */
public class TCountdownLatch {

    private static AtomicInteger no = new AtomicInteger(0);

    public static void main(String[] args) {
        doTask();
    }

    private static void doTask() {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                try {
                    int number = no.incrementAndGet();
                    System.out.println(String.format("no %s is start", number));
                    Thread.sleep(100);
                    System.out.println(String.format("no %s is end", number));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }

            }).start();
        }
        try {
            latch.await();
            System.out.println(String.format("main thread is end"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
