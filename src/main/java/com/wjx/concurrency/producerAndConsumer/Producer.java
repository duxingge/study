package com.wjx.concurrency.producerAndConsumer;

/**
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class Producer implements Runnable{

    private IStorage storage;


    @Override
    public void run() {
        while (true) {
            try {
                //这里模拟生产事件，不能放到锁里面，否则多个生产者不能并发
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storage.produce();
        }
    }

    public Producer(IStorage storage) {
        this.storage = storage;
    }
}
