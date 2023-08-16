package com.wjx;


import java.util.*;

/**
 *
 *
 */
public class Solution {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(3);
        TreeNode ll1 = new TreeNode(4);
        root.left = l1;
        root.right = r1;
        root.left.left = ll1;

        reverse(root);

    }

    static void reverse(TreeNode treeNode) {
        if(treeNode==null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList();

        queue.offer(treeNode);
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.val);
            if(node.left!=null) {
                queue.offer(node.left);
            }
            if(node.right!=null) {
                queue.offer(node.right);
            }
        }
    }



    static class TreeNode {
        int val;
        public TreeNode left;
        public TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}



