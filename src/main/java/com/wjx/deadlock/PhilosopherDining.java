package com.wjx.deadlock;

/**
 * 5名哲学家围桌吃饭，需要左右两根筷子才可吃饭，如何实现死锁？又如何避免死锁？
 * 扩展1：使用jps -l |  jstack PID观察死锁
 * @Author wangjiaxing
 * @Date 2022/9/12
 */
public class PhilosopherDining {

    public static void main(String[] args) {
        ChopSticks c0 = new ChopSticks();
        ChopSticks c1 = new ChopSticks();
        ChopSticks c2 = new ChopSticks();
        ChopSticks c3 = new ChopSticks();
        ChopSticks c4 = new ChopSticks();

        Philosopher p0 = new Philosopher(0, c0, c1);
        Philosopher p1 = new Philosopher(1, c1, c2);
        Philosopher p2 = new Philosopher(2, c2, c3);
        Philosopher p3 = new Philosopher(3, c3, c4);
        Philosopher p4 = new Philosopher(4, c4, c0);
        p0.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
    }


    static class Philosopher extends Thread {
        int id;
        ChopSticks l;
        ChopSticks r;

        public Philosopher(int id, ChopSticks l, ChopSticks r) {
            this.id = id;
            this.l = l;
            this.r = r;
        }

//        算法1,都先抓右筷子，再抓左筷子，会出现死锁
        @Override
        public void run() {
            synchronized (r) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (l) {
                    System.out.println(id + " 吃完了");
                }
            }
        }


////        算法二 出现一个左撇子
//        @Override
//        public void run() {
//            if(id==0) {   // if(id%2==0) 算法三，偶数左撇子
//                synchronized (l) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    synchronized (r) {
//                        System.out.println(id + " 吃完了");
//                    }
//                }
//            }else {
//                synchronized (r) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    synchronized (l) {
//                        System.out.println(id + " 吃完了");
//                    }
//                }
//            }
//        }
    }


    static class ChopSticks {

    }


}
