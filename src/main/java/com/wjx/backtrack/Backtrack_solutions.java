package com.wjx.backtrack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *其实回溯算法和我们常说的 DFS 算法非常类似，本质上就是一种暴力穷举算法。回溯算法和 DFS 算法的细微差别是：回溯算法是在遍历「树枝」，DFS 算法是在遍历「节点」，本文就是简单提一下，等你看到后文 图论算法基础 时就能深刻理解这句话的含义了。
 *
 * 废话不多说，直接上回溯算法框架，解决一个回溯问题，实际上就是一个决策树的遍历过程，站在回溯树的一个节点上，你只需要思考 3 个问题：
 *
 * 1、路径：也就是已经做出的选择。
 *
 * 2、选择列表：也就是你当前可以做的选择。
 *
 * 3、结束条件：也就是到达决策树底层，无法再做选择的条件。
 *
 * result = []
 * def backtrack(路径, 选择列表):
 *     if 满足结束条件:
 *         result.add(路径)
 *         return
 *
 *     for 选择 in 选择列表:
 *         做选择
 *         backtrack(路径, 选择列表)
 *         撤销选择
 *
 *
 *
 *
 * @Author wangjiaxing
 * @Date 2023/4/28
 */
public interface Backtrack_solutions {


    /**
     * 回溯算法解决所有全排列/组合/子集框架。
     * 打印所有子集，无重复可选
     *
     */
    class Solution1 {

        List<List<Integer>> res = new LinkedList<>();
        // 记录回溯算法的递归路径
        LinkedList<Integer> track = new LinkedList<>();

        // 主函数
        public List<List<Integer>> subsets(int[] nums) {
            backtrack(nums, 0);
            return res;
        }

        // 回溯算法核心函数，遍历子集问题的回溯树
        void backtrack(int[] nums, int start) {

            // 前序位置，每个节点的值都是一个子集---根据具体条件修改，选择性加入结果。
            res.add(new LinkedList<>(track));

            // 回溯算法标准框架
            for (int i = start; i < nums.length; i++) {
                // 做选择
                track.addLast(nums[i]);
                // 通过 start 参数控制树枝的遍历，避免产生重复的子集
                backtrack(nums, i + 1);
                // 撤销选择
                track.removeLast();
            }
        }
    }

    /**
     * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
     *
     * 你可以按 任何顺序 返回答案。
     *
     *
     *
     * 示例 1：
     *
     * 输入：n = 4, k = 2
     * 输出：
     * [
     *   [2,4],
     *   [3,4],
     *   [2,3],
     *   [1,2],
     *   [1,3],
     *   [1,4],
     * ]
     * 示例 2：
     *
     * 输入：n = 1, k = 1
     * 输出：[[1]]
     *
     * https://leetcode.cn/problems/combinations/
     */
    class Solution {
        List<List<Integer>> res = new ArrayList<>();
        LinkedList trace = new LinkedList();

        public List<List<Integer>> combine(int n, int k) {
            backtrace(1,n,k);
            return res;
        }

        private void backtrace(int start, int n, int k) {
            if(trace.size()==k) {
                res.add(new LinkedList<>(trace));
                return;
            }

            for (int i = start ; i < n+1; i++) {
                trace.addLast(i);
                backtrace(i+1,n,k);
                trace.removeLast();
            }
        }
    }

}
