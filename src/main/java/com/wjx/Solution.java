package com.wjx;


import java.util.*;

public class Solution {
    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> traceList = new LinkedList<>();


    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] selections = new int[9];
        for (int i = 1; i < 10; i++) {
            selections[i-1] = i;
        }
        backTrace(0, selections, k , n);
        return res;
    }

    private void backTrace(int start, int[] selections, int k, int n) {
        if(k==0 && n==0) {
            res.add(new LinkedList<>(traceList));
        }
        if (n < 0 || k < 0 ) {
            return;
        }

        for (int i = start ; i < selections.length; i++) {
            traceList.addLast(selections[i]);
            backTrace(i+1 ,selections,k-1, n - selections[i]);
            traceList.removeLast();
        }
    }
}

