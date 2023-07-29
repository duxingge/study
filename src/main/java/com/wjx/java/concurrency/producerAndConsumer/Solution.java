package com.wjx.java.concurrency.producerAndConsumer;

/**
 * @Author wangjiaxing
 * @Date 2023/1/29
 */
public class Solution {

    public static void main(String[] args) {
        IStorage storage = new SemaphoreStorage();
        Producer p1 = new Producer(storage);
        Producer p2 = new Producer(storage);
        Producer p3 = new Producer(storage);
        Producer p4 = new Producer(storage);
        Customer c1 = new Customer(storage);
        Customer c2 = new Customer(storage);
        Customer c3 = new Customer(storage);
        Customer c4 = new Customer(storage);
        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(p3).start();
        new Thread(p4).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();
//        new Thread(c4).start();

    }
}
