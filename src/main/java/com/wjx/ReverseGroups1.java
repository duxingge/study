package com.wjx;


import java.util.LinkedList;
import java.util.Queue;

/**
 * //    kevinyyao@tencent.com
 * //    链表分组反转
 * //    从链表尾部开始组起，头部剩余节点数量不够一组的不需要逆序。
 * //    链表 1->2->3->4->5->6->7->8->null, K=3
 * //    那么 6->7->8->,3->4->5,1->2
 * //    调整后1->2->5->4->3->8->7->6->null
 *
 * @Author wangjiaxing
 * @Date 2023/8/3
 */
public class ReverseGroups1 {

    public Node reverseKGroup(Node head, int k) {

        int l = len(head);
        int start = l%k;
        Node preHead = new Node(-1);
        preHead.next = head;
        Node cur = head;
        for (int i = 0; i < start; i++) {
            cur = cur.next;
        }
        while (cur!=null) {
            cur = doreverse(cur,k);
        }
        return preHead.next;


    }

    private Node doreverse(Node cur, int k) {
        Queue<Node> q = new LinkedList();
        for (int i = 0; i < k; i++) {
            q.offer(cur);
            cur = cur.next;
        }
        Node poll = q.poll();
        Node pIndex = poll;
        while (!q.isEmpty()) {
            pIndex.next = q.poll();
            pIndex = pIndex.next;
        }
        pIndex.next = cur;
        return cur;
    }

    private int len(Node head) {
        Node c = head;
        int len = 0;
        while (c!=null) {
            len++;
            c = c.next;
        }
        return len;
    }


    public class Node {
          int val;
          Node next;

          Node(int val) {
              this.val = val;
          }
      }

}
