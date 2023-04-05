package com.wjx.binarytree;

import com.wjx.binarytree.kit.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 层序遍历：BFS(Breadth-First-Search)的基础
 * @Author wangjiaxing
 * @Date 2023/3/1
 */
public class LayerTraverse {

    void levelTraverse(TreeNode node) {
        if(node==null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(node);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if(poll.left!=null) {
                queue.offer(poll.left);
            }
            if(poll.right!=null) {
                queue.offer(poll.right);
            }
        }

    }
}
