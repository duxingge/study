package com.wjx.java.collection;

import com.google.common.collect.Lists;
import com.wjx.java.collection.model.Book;

import java.util.*;
import java.util.stream.Collectors;

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
 *              c.ArrayDeque虽然需要扩容，但是均摊下来顺序插入操作O(1),性能优于LinkedList(LinkedList虽然也是O(1),但每次都要申请新的堆空间)
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


    public static void main(String[] args) {
        collectionTemplate();
    }

    /**
     * 展示一些集合常用的操作
     */
    public static void collectionTemplate() {
        List<String> strs = Lists.newArrayList("s1", "s2", "s3");
        List<Book> books = Lists.newArrayList(new Book("math",30),new Book("Chinese",20));
        Integer[] integers = new Integer[] {2,45,67,99};
        int[] ints = new int[] {2,45,67,99};

        //1.集合判空, 使用isEmpty而不是size()==0, 因为一些集合的size()时间复杂度不是O(1)
        strs.isEmpty();

        //2.集合转Map,注意value不能为null,否则会NPE
        Map<String, Integer> name2PriceMap = books.stream().collect(Collectors.toMap(Book::getName, Book::getPrice));

        //3.集合遍历,不要在foeEach中add/remove操作
//        strs.forEach(it->strs.remove(it)); # 抛出ConcurrentModificationException，因为使用的是List.remove，而不是iterator.remove
        Iterator<String> iterator = strs.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }

        //3.1 JDK1.8之后支持removeIf
        strs.removeIf(s->s.equals("s1"));

        //4. 利用set去重,时间复杂度接近O(n)。如果使用for循环+contains来实现，时间复杂度为O(n2)
        Set<String> strings = new HashSet<>(strs);

        //5. 集合转数组,参数new String[0]启一个类型声明的作用
        String[] strArr = strs.toArray(new String[0]);

        //6. 数组转集合
        List<Integer> nums1 = Arrays.stream(integers).collect(Collectors.toList());
        List<Integer> nums2 = Arrays.stream(ints).boxed().collect(Collectors.toList());

    }
}
