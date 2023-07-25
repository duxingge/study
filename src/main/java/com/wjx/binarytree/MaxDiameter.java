package com.wjx.binarytree;

import com.wjx.binarytree.kit.TreeNode;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
 *
 *  
 *
 * 示例 :
 * 给定二叉树
 *
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/diameter-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Author wangjiaxing
 * @Date 2023/3/1
 */
public class MaxDiameter {

    int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        traverse(root);
        return maxDiameter;
    }

    /**
     * 对每个节点来说，需要计算该节点的左右节点最大深度之和.
     * 遍历所有节点，然后取   最大值
     * @param node
     * @return
     */
    public int traverse(TreeNode node) {
        if(node==null) {
            return 0;
        }

        int deepLeft = traverse(node.left);
        int deepRight = traverse(node.right);
        maxDiameter = Math.max(deepLeft+deepRight,maxDiameter);
        return Math.max(deepLeft,deepRight)+1;
    }
}
