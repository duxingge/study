package com.wjx.java.concurrency.thread;

/**
 * 线程应该如何优雅的停止？
 * 1.自然结束。
 * 2.使用interrupt来中断线程。
 * 注意：main线程thread1.interrupted()是判断并清除当前运行线程的中断状态，而不是thread1。
 *
 * @Author wangjiaxing
 * @Date 2023/2/14
 */
public class ThreadStop extends Thread {

    public void run() {
        super.run();
        try {
            for (int i = 0; i < 100000; i++) {
                //interrupted()的实现currentThread().isInterrupted(true);，当前线程是this线程,所以检测并清除的是this线程的中断状态
                if (this.interrupted()) {
                    System.out.println("线程已经终止， for循环不再执行");
                    throw new InterruptedException("");
                }
                i++;
                System.out.println("i=" + (i + 1));
            }

        } catch (InterruptedException e) {
            //捕获中断异常，进行清理工作
            System.out.println("线程已经终止， for循环不再执行");
            e.printStackTrace();
        }
    }


    public static void main(String args[]) {
        ThreadStop thread = new ThreadStop();
        thread.start();
        //为thread线程打上中断标志
        thread.interrupt();
    }
}


