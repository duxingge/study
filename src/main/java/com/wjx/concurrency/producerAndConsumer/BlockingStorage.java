package com.wjx.concurrency.producerAndConsumer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueue内部也是用ReentrantLock+Condition实现的，和 {@link ReentrantLockStorage} 实现一致
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class BlockingStorage implements IStorage{

    LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue(10);


    @Override
    public void produce() {
        try {
            queue.put(new Object());
            System.out.println(String.format("product is produce.....生产者线程:%s, 目前库存:%s",Thread.currentThread().getName(),queue.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consume() {
        try {
            queue.take();
            System.out.println(String.format("product is consume.....消费者线程:%s, 目前库存:%s",Thread.currentThread().getName(),queue.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
