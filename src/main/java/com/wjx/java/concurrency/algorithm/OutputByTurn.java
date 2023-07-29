package com.wjx.java.concurrency.algorithm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组a,b,c,d
 * 数组1,2,3,4
 * 两个线程，交替输出，如a1b2c3..... 如何实现?
 * 思路一:Object.wait()/Object.notifyAll()
 * 思路二:ReentrantLock的condition
 * 思路三: LockSupport.park() & LockSupport.unPark()
 * @Author wangjiaxing
 * @Date 2022/9/12
 */
public class OutputByTurn {
    static Thread t1 = null;
    static Thread t2 = null;
    static char[] chs = {'a', 'b', 'c', 'd', 'e'};
    static int[] nums = {1, 2, 3, 4, 5};

    public static void main(String[] args) {
        doWithObjectWait();
    }


    public static void doWithLockSupport() {
        //        //解法一，LockSupport
        t1 = new Thread(() -> {
            for (char ch : chs) {
                System.out.println(ch);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(()->{
            for (int num : nums) {
                LockSupport.park();
                System.out.println(num);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }



    public static void doWithObjectWait() {
        //        //解法二 Object wait/notify实现
        Object obj = new Object();
        CountDownLatch countDownLatch = new CountDownLatch(1);


        t1 = new Thread(() -> {
            synchronized (obj) {
                countDownLatch.countDown();
                for (char ch : chs) {
                    System.out.println(ch);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj.notify();  // 没有这句线程不会结束
            }



        });

        t2 = new Thread(() -> {
            try {
                countDownLatch.await(); //让t1先执行先拿到锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                for (int n : nums) {
                    System.out.println(n);
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj.notify();
            }
        });
        t1.start();
        t2.start();
    }


    public static void doWithReentrantLock() {
        //解法三，ReentrantLock
        ReentrantLock lock = new ReentrantLock();
        Condition conditionCh = lock.newCondition();
        Condition conditionNum = lock.newCondition();

        t1 = new Thread(() -> {
            lock.lock();
            try{
                for (char ch : chs) {
                    System.out.println(ch);
                    conditionNum.signal();
                    try {
                        conditionCh.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                conditionNum.signal();
            }finally {
                lock.unlock();
            }
        });

        t2 = new Thread(() -> {
            lock.lock();
            try{
                for (int nu : nums) {
                    System.out.println(nu);
                    conditionCh.signal();
                    try {
                        conditionNum.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                conditionCh.signal();
            }finally {
                lock.unlock();
            }
        });
    }
}
