package com.wjx.twopoint;

import java.util.PriorityQueue;

/**
 *
 */
public class TwoPoint_1 {
    public static void main(String[] args) {

    }


//
//

    /**
     * Q:给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * 链接：https://leetcode.cn/problems/partition-list
     * <p>
     * A:两个节点分别链接小于的节点，等于大于的节点。最后合并
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smallPre = new ListNode(-1);
        ListNode eqOrBigPre = new ListNode(-1);
        ListNode index = head, smallIndex = smallPre, eqOrBigIndex = eqOrBigPre;
        while (index != null) {
            if (index.val < x) {
                smallIndex.next = index;
                smallIndex = index;
            } else {
                eqOrBigIndex.next = index;
                eqOrBigIndex = index;
            }
            ListNode temp = index;
            index = index.next;
            temp.next = null;
        }
        smallIndex.next = eqOrBigPre.next;
        return smallPre.next;
    }

    /**
     * Q:给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。k=lists.length, n=每个链表的平均长度
     * <p>
     * A1: 顺序合并（（1，2），3）.....两个两个做归并。 O(n) = O(k^2 * n)
     * A2: 分治合并 ((1,2), (3,4))....  O(n)= O(kn*logk)
     * A3: 优先级队列 小顶堆思想，kn个数，每次调整logN, O(n)= O(kn*logk)
     * https://leetcode.cn/problems/merge-k-sorted-lists/
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKListsA2(lists);
    }

    public ListNode mergeKListsA3(ListNode[] lists) {

        if (lists.length == 0) return null;
        // 虚拟头结点
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        // 优先级队列，最小堆
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                lists.length, (a, b)->(a.val - b.val));
        // 将 k 个链表的头结点加入最小堆
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }

        while (!pq.isEmpty()) {
            // 获取最小节点，接到结果链表中
            ListNode node = pq.poll();
            p.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            // p 指针不断前进
            p = p.next;
        }
        return dummy.next;

    }




    public ListNode mergeKListsA2(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        return process(lists, 0, lists.length - 1);
    }

    public ListNode process(ListNode[] lists, int start, int end) {
        if (end == start) {
            return lists[start];
        }
        if (end < start) {
            return null;
        }
        int mid = start + ((end - start) >> 1);
        return mergeNode(process(lists, start, mid), process(lists, mid + 1, end));
    }


    public ListNode mergeNode(ListNode node1, ListNode node2) {
        ListNode preNode = new ListNode(-1);
        ListNode index = preNode;
        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                index.next = node1;
                node1 = node1.next;
            } else {
                index.next = node2;
                node2 = node2.next;
            }
            index = index.next;
        }
        if (node1 != null) {
            index.next = node1;
        }
        if (node2 != null) {
            index.next = node2;
        }
        return preNode.next;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
