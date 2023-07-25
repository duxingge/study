package com.wjx.backtrack;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangjiaxing
 * @Date 2023/7/25
 */
public class KsumN {

    class Solution {

        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> combinationSum3(int k, int n) {
            List<Integer> integers = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                integers.add(i);
            }
            backTrace(integers, k , n);
            return res;
        }

        private void backTrace(List<Integer> integers, int k, int n) {
            if(k==0 && n==0) {
                res.add(integers);
            }
            if (n < 0 || k < 0) {
                return;
            }
            for (int i = 0; i < integers.size(); i++) {
                final ArrayList<Integer> integersCopy = Lists.newArrayList(integers);
                integersCopy.remove(integers.get(i));
                backTrace(integersCopy, k - 1, n - integers.get(i));
            }
        }
    }
}
