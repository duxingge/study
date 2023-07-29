package com.wjx.bfs;

import com.wjx.util.JsonUtil;

import java.util.*;

/**
 * @Author wangjiaxing
 * @Date 2023/7/28
 */
public class BFS_openLock {


    static class Solution {
        Queue<String> q = new LinkedList();
        int times = 0;
        String aim = null;
        Set<String> deadendStrs;
        Set<String> visited = new HashSet<>();
        public int openLock(String[] deadends, String target) {
            deadendStrs = new HashSet(Arrays.asList(deadends));
            aim = target;
            BFS();
            return times;
        }

        void BFS() {
            q.offer("0000");
            visited.add("0000");
            while(!q.isEmpty()) {
                int size = q.size();
                System.out.println("------" + q.size());
                for( int i=0;i<size ; i++) {
                    String cur = q.poll();
                    if(aim.equals(cur)) {
                        return;
                    }
                    if(deadendStrs.contains(cur)) {
                        continue;
                    }
                    for(int j=0; j < cur.length(); j++) {
                        String addOne = addOneAt(cur, j);
                        String minusOne = minusOneAt(cur, j);
                        if(!visited.contains(addOne)) {
                            q.offer(addOne);
                            visited.add(addOne);
                        }
                        if(!visited.contains(minusOne)) {
                            q.offer(minusOne);
                            visited.add(minusOne);
                        }
                    }
                }
                times++;

            }
        }
        String addOneAt(String cur, int j) {
            char[] chars = cur.toCharArray();
            if(chars[j]=='9') {
                chars[j] = '0';
            }else {
                chars[j] += 1;
            }

            return new String(chars);
        }

        String minusOneAt(String cur, int j) {
            char[] chars = cur.toCharArray();
            if(chars[j]=='0') {
                chars[j] = '9';
            }else {
                chars[j] -= 1;
            }
            return new String(chars);
        }

    }

    public static void main(String[] args) {
        System.out.println(new Solution().openLock(new String[]{}, "5590"));
    }



}
