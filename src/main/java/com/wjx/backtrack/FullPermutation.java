package com.wjx.backtrack;

import com.google.common.collect.Lists;
import com.wjx.util.JsonUtil;

import java.util.*;

/**
 * 全排列问题: 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 *
 * @Author wangjiaxing
 * @Date 2023/4/5
 */
public class FullPermutation {


    public static void main(String[] args) {
        System.out.println(JsonUtil.toString(new FullPermutation().permute(new int[]{1, 2, 3})));
    }

        List<List<Integer>> res = new LinkedList<>();

        /* 主函数，输入一组不重复的数字，返回它们的全排列 */
        List<List<Integer>> permute(int[] nums) {
            // 记录「路径」
            LinkedList<Integer> track = new LinkedList<>();
            // 「路径」中的元素会被标记为 true，避免重复使用
            boolean[] used = new boolean[nums.length];

            backtrack(nums, track, used);
            return res;
        }

        // 路径：记录在 track 中
        // 选择列表：nums 中不存在于 track 的那些元素（used[i] 为 false）
        // 结束条件：nums 中的元素全都在 track 中出现
        void backtrack(int[] nums, LinkedList<Integer> track, boolean[] used) {
            // 触发结束条件
            if (track.size() == nums.length) {
                res.add(new LinkedList(track));
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                // 排除不合法的选择
                if (used[i]) {
                    // nums[i] 已经在 track 中，跳过
                    continue;
                }
                // 做选择
                track.add(nums[i]);
                used[i] = true;
                // 进入下一层决策树
                backtrack(nums, track, used);
                // 取消选择
                track.removeLast();
                used[i] = false;
            }
        }
}
