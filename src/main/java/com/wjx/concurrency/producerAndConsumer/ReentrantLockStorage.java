package com.wjx.concurrency.producerAndConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 基于AQS实现，而AQS的线程等待和唤醒也是基于LockSupport的park/unpark()方法。
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class ReentrantLockStorage implements IStorage{

    final int MAX_CAPITAL = 10;

    private final LinkedList list = new LinkedList();

    // 锁
    private final Lock lock = new ReentrantLock();

    // 仓库满的条件变量
    private final Condition full = lock.newCondition();
    // 仓库空的条件变量
    private final Condition empty = lock.newCondition();


    @Override
    public void produce() {

        try {
            lock.lock();
            //如果不使用while而用if，可能出现爆仓， 因为可能通知线程notify到线程执行期间，仓库容量可能会发生变化。
            while (list.size()>=MAX_CAPITAL) {
                System.out.println("仓库已满，无法生产...");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(new Object());
            System.out.println(String.format("product is produce.....生产者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
            empty.signalAll();
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void consume() {
        try{
            lock.lock();
            while (list.size()==0) {
                System.out.println("仓库已空，无法消费...");
                try {
                    empty.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.remove();
            full.signalAll();
            System.out.println(String.format("product is consume.....消费者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
        }finally {
            lock.unlock();
        }
    }
}
