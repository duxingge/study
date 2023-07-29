package com.wjx.java.concurrency.producerAndConsumer;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 1. Semaphore 初始permit可以为负数,但必须release正数后才可acquire
 *
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class SemaphoreStorage implements IStorage{

    Semaphore notFull = new Semaphore(10);
    Semaphore notEmpty = new Semaphore(0);

    Semaphore mutex = new Semaphore(1);

    LinkedList<Object> list = new LinkedList<>();

    @Override
    public void produce() {

        try {
            notFull.acquire();
            //LinkedList不是线程安全类,所以需要mutex进行同步
            mutex.acquire();
            list.add(new Object());
            System.out.println(String.format("product is produce.....生产者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            notEmpty.release();
            mutex.release();
        }

    }

    @Override
    public void consume() {
        try {
            notEmpty.acquire();
            mutex.acquire();
            //LinkedList不是线程安全类,所以需要mutex进行同步
            list.remove();
            System.out.println(String.format("product is consume.....消费者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            notFull.release();
            mutex.release();
        }

    }
}
