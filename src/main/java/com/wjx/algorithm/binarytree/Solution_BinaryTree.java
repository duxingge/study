package com.wjx.algorithm.binarytree;

import com.wjx.algorithm.binarytree.kit.TreeNode;

/**
 *二叉树题目的递归解法可以分两类思路:
 *  第一类是遍历一遍二叉树得出答案，
 *  第二类是通过分解问题计算出答案，
 *  这两类思路分别对应着 回溯算法核心框架 和 动态规划核心框架。
 *
 *  for example {@link MaxDepth}
 *
 *  综上，遇到一道二叉树的题目时的通用思考过程是：
 * 1、是否可以通过遍历一遍二叉树得到答案？如果可以，用一个 traverse 函数配合外部变量来实现。
 *
 * 2、是否可以定义一个递归函数，通过子问题（子树）的答案推导出原问题的答案？如果可以，写出这个递归函数的定义，并充分利用这个函数的返回值。
 *
 * 3、无论使用哪一种思维模式，你都要明白二叉树的每一个节点需要做什么，需要在什么时候（前中后序）做。
 *
 * @Author wangjiaxing
 * @Date 2023/3/1
 */
public class Solution_BinaryTree {

    /**
     * 遍历：
     * 二叉树的所有问题，就是让你在前中后序位置注入巧妙的代码逻辑，去达到自己的目的，
     * 你只需要单独思考每一个节点的每一个前中后序位置应该做什么，其他的不用你管，抛给二叉树遍历框架，递归会在所有节点上做相同的操作。
     * @param node
     */
    void traverse(TreeNode node) {
        //node节点的前序位置，代表进入node节点
        traverse(node.left);
        //node节点的中序位置
        traverse(node.right);
        //node节点的后序位置，代表离开node节点,后续位置可以获得左右子树的信息
    }
}
