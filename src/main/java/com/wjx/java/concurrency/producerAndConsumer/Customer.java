package com.wjx.java.concurrency.producerAndConsumer;

/**
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class Customer implements Runnable{
    IStorage storage;


    public Customer(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storage.consume();
        }

    }
}
