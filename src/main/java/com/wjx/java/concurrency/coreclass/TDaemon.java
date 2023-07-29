package com.wjx.java.concurrency.coreclass;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程是JVM级别的，web容器关闭（即主线程结束）时，非守护线程不会结束。也就是说非守护线程与web容器的生命周期并不会保持一致。
 * 也就是说，即使你停止了web应用，非守护线程仍在运行。
 * 1。守护线程应该永远不去访问固有资源，如文件、数据库或计算逻辑，因为它会在任何时候甚至在一个操作的中间发生中断。造成这个结果理由已经说过了：一旦所有User Thread离开了，虚拟机也就退出运行了。
 * 2。守护进程适合一些后台进程，如垃圾收集，状态监控等。
 * 3。Daemon线程中新建的线程也是Daemon线程
 *
 * @Author wangjiaxing
 * @Date 2023/1/27
 */
public class TDaemon {
    static AtomicInteger i = new AtomicInteger();

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            while (true) {
                int iv = TDaemon.i.incrementAndGet();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iv);
            }
        });
//        thread.setDaemon(true);
        thread.start();
        System.out.println("main thread is end");
    }
}
