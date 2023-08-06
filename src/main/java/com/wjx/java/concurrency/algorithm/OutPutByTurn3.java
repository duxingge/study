package com.wjx.java.concurrency.algorithm;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class OutPutByTurn3 {


    public static void doWithReentrantLock() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        int times = 3;
        Thread t1 = new Thread(() -> {
            lock.lock();
            try{
                countDownLatch1.countDown();
                for (int i = 0; i < 3; i++) {
                    System.out.println("1");
                    condition2.signal();
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                condition2.signal();
            }finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                countDownLatch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try{
                countDownLatch2.countDown();
                for (int i = 0; i < 3; i++) {
                    System.out.println("2");
                    condition3.signal();
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                condition3.signal();
            }finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                countDownLatch1.await();
                countDownLatch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try{
                for (int i = 0; i < 3; i++) {
                    System.out.println("3");
                    condition1.signal();
                    try {
                        condition3.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                condition1.signal();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

    public static void main(String[] args) {
        doWithReentrantLock();
    }


}

