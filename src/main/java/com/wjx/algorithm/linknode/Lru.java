package com.wjx.algorithm.linknode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 *
 *
 * https://leetcode.cn/problems/lru-cache/solutions/259678/lruhuan-cun-ji-zhi-by-leetcode-solution/
 * @Author wangjiaxing
 * @Date 2023/7/30
 */
public class Lru {

    class LRUCache {
        Map<Integer,Node> cache;
        int capacity = 0;
        Node head;
        Node tail;


        public LRUCache(int capacity) {
            cache = new HashMap<>(capacity);
            this.capacity = capacity;
            head = new Node(null,null);
            tail = new Node(null,null);
            head.next = tail;
            tail.pre = head;
        }

        public int get(int key) {
            Node node = cache.get(key);
            if(node == null) {
                return -1;
            }
            move2Head(node);
            return node.value;
        }


        void move2Head(Node node) {
            removeNode(node);
            add2Head(node);
        }

        void add2Head(Node node) {
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            node.pre = head;
        }

        void removeNode(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        public void put(int key, int value) {
            Node cacheNode = cache.get(key);
            if(cacheNode!=null) {
                cacheNode.value = value;
                move2Head(cacheNode);
                return;
            }else {
                Node node = new Node(key,value);
                add2Head(node);
                cache.put(key,node);
                if(cache.size()>capacity) {
                    Node rNode = tail.pre;
                    cache.remove(rNode.key);
                    removeNode(rNode);

                }

            }



        }

        private void printNodes() {
            Node index = head;
            System.out.print("node: ");
            while (index!=null) {
                System.out.print(index.key + " ");
                index = index.next;
            }
            System.out.println("");

        }


        class Node {
            Integer value;
            Integer key;
            Node pre;
            Node next;

            public Node(Integer key, Integer value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
