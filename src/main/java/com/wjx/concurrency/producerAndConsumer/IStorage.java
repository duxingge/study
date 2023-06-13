package com.wjx.concurrency.producerAndConsumer;

/**
 * 生产者消费者模式，也是有限缓冲问题。本质是进库出库的加锁同步，而不是生产过程和消费过程的加锁同步.
 *
 * 主流的实现有：
 * wait/notifyAll {@link WaitAndNotifyStorage}
 * ReentrantLock await/signalAll {@link ReentrantLockStorage}
 * BlockingQueue put/take {@link BlockingStorage} 本质也是基于ReentrantLock await/signalAll
 * Semaphore 信号量方式实现 比较麻烦。{@link SemaphoreStorage } 管程是信号量的封装，所以基于管程mesa模型的ReentrantLock/BlockingQueue比Semaphore更简单。
 *
 * 注1: {@link java.util.concurrent.locks.AbstractQueuedSynchronizer}是信号量思想
 * 注2: {@link java.util.concurrent.locks.ReentrantLock} ReentrantLock是基于AQS结合condition实现的，属于管程思想
 *
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public interface IStorage {
    void produce();
    void consume();
}
