package com.wjx.collection;

/**
 *  Collection接口主要有三种实现: Set,List,Queue. 除此之外还有Map集合
 *  1.Set:
 *      {@link java.util.HashSet} (无序，唯一) 基于HashMap实现，底层采用HashMap保存元素
 *      {@link java.util.LinkedHashSet} （有序，唯一）基于LinkHashMap实现
 *      {@link java.util.TreeSet} （有序，唯一）红黑树实现
 *
 *  2.List:
 *      {@link java.util.ArrayList} Object[]数组实现
 *          一.扩容机制：
 *              a.无参数的默认构造器默初始容器长度为0,第一次扩容变为10,
 *                  其后 newCapacity = oldCapacity + (oldCapacity >> 1), 即每次1.5倍左右扩容,最大{@link Integer#MAX_VALUE}
 *      {@link java.util.Vector} Object[]数组实现，线程安全类(通过将所有public方法加synchronized来保证线程安全)，效率低下，
 *              在读多写少的情况下可用{@link java.util.concurrent.CopyOnWriteArrayList} 替代
 *      {@link java.util.LinkedList} 双向链表实现
 *
 *  3.Queue:
 *      {@link java.util.PriorityQueue} Object[]实现的大/小顶堆(默认小顶堆)
 *      {@link java.util.ArrayDeque} Object[] + 双指针实现。
 *          VS {@link java.util.LinkedList}
 *              a.都实现了Deque接口.
 *              b.ArrayDeque不支持NULL,但LinkedList支持
 *              c.ArrayDeque插入操作O(1),性能优于LinkedList
 *
 *
 *  4.Map
 *      {@link java.util.HashMap}
 *          jdk 1.8之前数组+链表(拉链法解决的Hash冲突)实现,
 *          jdk 1.8后改为数组+链表/红黑树实现(链表长度大于8时：如果数组长度小于64则数组扩容，否则链表转为红黑树结构)
 *          一，扩容机制：
 *              a.数组默认初始大小为16
 *              b.数组的长度N总是2的次幂大小扩张, 因为计算元素存放位置时采用(N-1) & hash, 2的次幂能保证均匀分布
 *
 *      {@link java.util.LinkedHashMap} 和HashMap实现基本一样，但另外维护了一个链表记录节点的加入容器顺序
 *      {@link java.util.Hashtable} 和Jdk1.8之前的HashMap一样，Object[]+链表实现。
 *              是线程安全类(通过将所有public方法加synchronized来保证线程安全)，但效率低下,可用{@link java.util.concurrent.ConcurrentHashMap}替代
 *      {@link java.util.TreeMap} 红黑树实现
 *
 * @Author wangjiaxing
 * @Date 2023/2/15
 */
public class Collection {
}
