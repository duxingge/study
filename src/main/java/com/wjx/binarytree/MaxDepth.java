package com.wjx.binarytree;

import com.wjx.binarytree.kit.TreeNode;

/**
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-depth-of-binary-tree
 * @Author wangjiaxing
 * @Date 2023/3/1
 */



class MaxDepth {
    int deep = 0;
    int maxDeep = 0;
    //遍历解法
    public int maxDepth(TreeNode node) {
        traverse(node);
        return maxDeep;
    }


    void traverse(TreeNode node) {
        //node节点的前序位置，代表进入node节点
        if(node==null) {
            return ;
        }
        deep++;
        if(node.left==null&&node.right==null) {
            maxDeep = Math.max(deep,maxDeep);
        }
        traverse(node.left);
        //node节点的中序位置
        traverse(node.right);
        //node节点的后序位置，代表离开node节点
        deep--;
    }


    //递归解法
    public int maxDepth2(TreeNode root) {
        if(root==null) {
            return 0;
        }
        return 1+Math.max(maxDepth2(root.left),maxDepth2(root.right));

    }
}
