package com.wjx.slidewindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 字串问题都可以使用滑动窗口
 * @Author wangjiaxing
 * @Date 2022/5/9
 */
public class Windows {
    /**
     * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     *
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     *
     *
     *
     * 示例1:
     *
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     * 示例 2:
     *
     * 输入: s = "abab", p = "ab"
     * 输出: [0,1,2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     *
     *
     * 提示:
     *
     * 1 <= s.length, p.length <= 3 * 104
     * s和p仅包含小写字母
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.length() < p.length()) {
            return res;
        }
        int[] xNums = new int[26];
        int[] yNums = new int[26];
        int diff = 0;

        char[] chars = p.toCharArray();
        for (int i = 0; i < p.toCharArray().length; i++) {
            yNums[chars[i] - 'a']++;
        }
        char[] sChars = s.toCharArray();
        for (int i = 0; i < p.length(); i++) {
            xNums[sChars[i] - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            diff += minus(xNums[i],yNums[i]);
        }
        if (diff == 0) {
            res.add(0);
        }
        for (int i = 0; i < (sChars.length - p.length()); i++) {
            int x1 = sChars[i] - 'a';
            int x2 = sChars[i+p.length()] - 'a';
            diff-=minus(xNums[x1],yNums[x1]);
            diff-=minus(xNums[x2],yNums[x2]);
            xNums[x1]--;
            xNums[x2]++;
            diff+=minus(xNums[x1],yNums[x1]);
            diff+=minus(xNums[x2],yNums[x2]);
            if (diff==0) {
                res.add(i + 1);
            }
        }
        return res;
    }

    int minus(int a, int b) {
        return a > b ? a - b : b - a;
    }
}
