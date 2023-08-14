package com.wjx.algorithm.linknode;

import com.wjx.algorithm.binarytree.kit.ListNode;

import java.util.PriorityQueue;

/**
 * 给你一个链表数组，每个链表都已经按升序排列。
 *
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 *
 *
 * 示例 1：
 *
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 *
 * @Author wangjiaxing
 * @Date 2023/8/9
 */
public class MergeKList {

    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            return mergeKListsWithPriority(lists);

        }
    }

    /**
     * O(t) = NlogK
     * 利用优先级队列的小顶堆排序
     * @param lists
     * @return
     */
    ListNode mergeKListsWithPriority(ListNode[] lists) {
        if(lists.length == 0) {
            return null;
        }

        ListNode dummp = new ListNode(-1);
        ListNode p = dummp;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, (a, b)->(a.val - b.val));
        for(int i=0;i<lists.length;i++) {
            if(lists[i]!=null) {
                queue.offer(lists[i]);
            }

        }

        while(!queue.isEmpty()) {
            ListNode min = queue.poll();
            p.next = min;
            p = min;
            if(min.next!=null) {
                queue.offer(min.next);
            }
        }
        return dummp.next;
    }
}
