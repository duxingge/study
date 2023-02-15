package com.wjx.concurrency.producerAndConsumer;

import java.util.LinkedList;

/**
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class WaitAndNotifyStorage implements IStorage {
    private final int MAX_CAPACITY = 10;
    private LinkedList<Object> list = new LinkedList<>();


    @Override
    public void produce() {
        synchronized (list) {
            while (list.size()>=MAX_CAPACITY) {
                System.out.println("仓库已满，无法生产...");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(new Object());
            System.out.println(String.format("product is produce.....生产者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
            // 会同时唤醒生产者和消费者，因此前面需要while来循环判断
            list.notifyAll();
        }
    }

    @Override
    public void consume() {
        synchronized (list) {
            while (list.size()==0) {
                System.out.println("仓库已空，无法消费...");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.remove();
            System.out.println(String.format("product is consume.....消费者线程:%s, 目前库存:%s",Thread.currentThread().getName(),list.size()));
            list.notifyAll();
        }
    }


}
