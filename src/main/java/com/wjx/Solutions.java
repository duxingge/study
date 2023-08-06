package com.wjx;

/**
 *
 * static 仅为了测试方便
 * @Author wangjiaxing
 * @Date 2023/8/4
 */
public class Solutions {



    static int leftSum = 0;
    static int rightSum = 0;


    static int leftMinusRight(Node root) {
        if(root==null) {
            return 0;
        }
        traverse(root.left,true);
        traverse(root.right,false);
        return leftSum - rightSum;
    }

    private static void traverse(Node node, boolean isLeft) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            traverse(node.left, true);
        }
        if (node.right != null) {
            traverse(node.right, false);
        }
        if (node.left == null && node.right == null) {
            if (isLeft) {
                leftSum+=node.val;
            } else {
                rightSum+=node.val;
            }
        }
    }


    static class Node {
        int val;
        Node left;
        Node right;

        Node(int val) {
            this.val = val;
        }

    }

    public static void main(String[] args) {
        Node root = new Node(-1);
        Node left1 = new Node(3);
        Node right1 = new Node(3);
        root.left = left1;
        root.right = right1;
        left1.left = new Node(4);
        left1.right = new Node(1);
        right1.left = new Node(5);
        right1.right = new Node(3);
        System.out.println(leftMinusRight(root));

    }


}
