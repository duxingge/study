package com.wjx.algorithm.binarytree.kit;

/**
 * @Author wangjiaxing
 * @Date 2023/8/9
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
