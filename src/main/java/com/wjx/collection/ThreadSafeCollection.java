package com.wjx.collection;

/**
 * 1. {@link java.util.concurrent.ConcurrentHashMap} 线程安全的HashMap
 *  JDK1.7之前： Segment+HashEntry数组+链表
 *      默认由16个segment(segment继承ReentrantLock)组成,因此默认最大支持16个线程并发。每个Segment就是一个HashMap结构，且都可segment内部扩容。
 *      a.put一个数据时:
 *          1.计算put的segment的位置
 *          2.如果segment为空，则初始化这个segment
 *          3.segment.put插入key,value元素(会先tryLock获取锁后，才执行之后的逻辑)
 *  JDK1.8之后: (改进的原因是jvm对synchronized进行了大量的优化,synchronized不再那么重了)
 *          Node[]数组+链表/红黑树
 *          a.put一个数据时：
 *              1.根据key计算出hashcode
 *              2.确认Node[]数组是否需要初始化。
 *              3.如果key对应的Node为空时，CAS写入值
 *              4.如果当前位置的 hashcode == MOVED == -1,则需要进行扩容。
 *              5.如果都不满足，则对index对应的Node节点加synchronized锁写入数据
 *              6.数组长度>64且链表长度>阀值转为红黑树
 *   总结：
 *   Java7 中 ConcurrentHashMap 使用的分段锁，也就是每一个 Segment 上同时只有一个线程可以操作，
 *      每一个 Segment 都是一个类似 HashMap 数组的结构，它可以扩容，它的冲突会转化为链表。
 *      但是 Segment 的个数一但初始化就不能改变。
 *   Java8 中的 ConcurrentHashMap 使用的 Synchronized 锁 + CAS 的机制。
 *      结构也由 Java7 中的 Segment 数组 + HashEntry 数组 + 链表 进化成了 Node 数组 + 链表 / 红黑树，
 *      Node 是类似于一个 HashEntry 的结构。它的冲突再达到一定大小时会转化成红黑树，在冲突小于一定数量时又退回链表。
 * @Author wangjiaxing
 * @Date 2023/2/15
 */
public class ThreadSafeCollection {
}
