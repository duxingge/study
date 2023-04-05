package com.wjx;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Solution {
    static char[] chs = {'a', 'b', 'c', 'd', 'e'};
    static int[] nums = {1, 2, 3, 4, 5};


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition charCondition = reentrantLock.newCondition();
        Condition numCondition = reentrantLock.newCondition();

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                reentrantLock.lock();
                for (char ch : chs) {
                    System.out.println(ch);
                    numCondition.signalAll();
                    charCondition.await();
                }
                numCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }).start();

        new Thread(() -> {
            reentrantLock.lock();

            try {
                for (int num : nums) {
                    System.out.println(num);
                    charCondition.signalAll();
                    numCondition.await();
                }
                charCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }

        }).start();


    }


}
