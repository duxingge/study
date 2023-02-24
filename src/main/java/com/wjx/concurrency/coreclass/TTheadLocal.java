package com.wjx.concurrency.coreclass;

/**
 * 赋予每个线程专有的变量
 *  1。每个线程都有一个ThreadlocalMap对象,里面有Entry[]（Entry extends WeakReference<ThreadLocal<?>>）
 *      一个Entry就是个key-value(ThreadLocal-value)
 *  2.内存泄漏问题： entry对TheadLocal是个弱引用，因此当ThreadLocal的强引用为零时会清除该Threadlocal，
 *      但线程对Entry(此时是key为null的entry)还保留强引用，因此ThreadLocal在使用完后应该调用remove，否则有内存泄漏问题
 *  3.
 * @Author wangjiaxing
 * @Date 2023/2/22
 */
public class TTheadLocal {
}
