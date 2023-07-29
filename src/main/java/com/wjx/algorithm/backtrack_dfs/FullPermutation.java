package com.wjx.algorithm.backtrack_dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 全排列问题： 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * https://leetcode.cn/problems/permutations/
 * @Author wangjiaxing
 * @Date 2023/4/28
 */
public class FullPermutation implements Backtrack_solutions{

    class Solution {

        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> trace = new LinkedList();
        boolean[] used;

        public List<List<Integer>> permute(int[] nums) {
            used = new boolean[nums.length];
            Arrays.fill(used,false);
            backtrace(nums);
            return res;
        }

        private void backtrace(int[] nums) {
            if(trace.size()==nums.length) {
                res.add(new LinkedList<>(trace));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if(used[i]) {
                    continue;
                }
                used[i] = true;
                trace.addLast(nums[i]);
                backtrace(nums);
                trace.removeLast();
                used[i] = false;
            }
        }
    }

}
