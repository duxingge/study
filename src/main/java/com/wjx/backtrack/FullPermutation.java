package com.wjx.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 全排列问题： 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * @Author wangjiaxing
 * @Date 2023/4/28
 */
public class FullPermutation implements Backtrack_solutions{


    class Solution {
        List<List<Integer>> result = new ArrayList<>();

        public List<List<Integer>> permute(int[] nums) {
            List<Integer> selections = new ArrayList<>();
            for (int num : nums) {
                selections.add(num);
            }
            backtrack(new ArrayList<>(), selections);
            return result;

        }

        private void backtrack(ArrayList<Integer> selected, List<Integer> selections) {
            if(selections.isEmpty()) {
                List<Integer> clone = new ArrayList<>(selected);
                result.add(clone);
            }
            Integer[] selectionArrs = selections.toArray(new Integer[0]);
            for (Integer n : selectionArrs) {
                selected.add(n);
                selections.remove(n);
                backtrack(selected,selections);
                selections.add(n);
                selected.remove(n);
            }
        }

    }

}
