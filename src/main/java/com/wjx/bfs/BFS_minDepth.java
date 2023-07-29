package com.wjx.bfs;

import com.wjx.binarytree.kit.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 *
 * 给定一个二叉树，找出其最小深度。
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 * 说明：叶子节点是指没有子节点的节点。
 *
 *
 *
 * 示例 1：
 *
 *
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 * 示例 2：
 *
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 *
 *
 * 提示：
 *
 * 树中节点数的范围在 [0, 105] 内
 * -1000 <= Node.val <= 1000
 *
 *
 * @Author wangjiaxing
 * @Date 2023/7/28
 */
public class BFS_minDepth {

    int depth = 0;
    Queue<TreeNode> q = new LinkedList<TreeNode>();


    public int minDepth(TreeNode root) {
        BFS(root);
        return depth;
    }

    void BFS(TreeNode node) {
        if(node == null) {
            return;
        }
        q.offer(node);
        while(!q.isEmpty()) {
            depth++;
            int size = q.size();
            for(int i=0; i<size; i++ ) {
                TreeNode cur = q.poll();
                if( cur.left==null && cur.right == null ) {
                    return;
                }
                if(cur.left !=null) {
                    q.offer(cur.left);
                }

                if(cur.right !=null) {
                    q.offer(cur.right);
                }
            }
        }
    }
}
