package com.wjx;


import java.util.*;

class Solution {
    int max = 0;
    List<Integer>[] paths;
    Map<Integer,Integer> nMaxReach = new HashMap();
    int[] times = null;
    public int minimumTime(int n, int[][] relations, int[] time) {
        paths = new List[time.length+1];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = new ArrayList<>();
        }
        times = time;
        for (int[] relation : relations) {
            paths[relation[1]].add(relation[0]);
        }
        for(int i=0 ; i< time.length; i++ ) {
            max = Math.max(max, time[i] + dp(i+1));
        }
        return max;
    }


    int dp(Integer classNum) {
        Integer cache = nMaxReach.get(classNum);
        if(cache !=null) {
            return cache;
        }

        int reachMax = 0;
        if(!paths[classNum].isEmpty()) {
            for(Integer p : paths[classNum]) {
                reachMax = Math.max(reachMax,times[p-1] + dp(p));
            }
        }
        nMaxReach.put(classNum, reachMax);
        return reachMax;
    }

    public static void main(String[] args) {
        final int[][] ints = new int[2][2];
        ints[0][0] = 1;
        ints[0][1] = 3;
        ints[1][0] = 2;
        ints[1][1] = 3;
        System.out.println(new Solution().minimumTime(3, ints, new int[]{3, 2, 5}));
    }
}

