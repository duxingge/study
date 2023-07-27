package com.wjx.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 *
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 *
 * 你可以认为每种硬币的数量是无限的。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：coins = [1, 2, 5], amount = 11
 * 输出：3
 * 解释：11 = 5 + 5 + 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author wangjiaxing
 * @Date 2023/3/1
 */
public class Solution_money {
    Map<Integer,Integer> amount2Count = new HashMap<>();

    public int coinChange(int[] coins, int amount) {
//        return  coinChange1(coins,amount);
        return coinChange2(coins,amount);
    }

    /**
     * 自顶而下备忘录方式
     * @param coins
     * @param amount
     * @return
     */
    int coinChange1(int[] coins, int amount) {
        if(amount==0) {
            return 0;
        }
        if(amount2Count.containsKey(amount)) {
            return amount2Count.get(amount);
        }
        int cc = -1;
        for (int coin : coins) {
            if(coin==amount) {
                return 1;
            }else if(amount>coin) {
                final int subC = coinChange1(coins, amount - coin);
                if(subC!=-1) {
                    cc = cc == -1? subC+1:Math.min(cc,subC+1);
                }
            }
        }
        amount2Count.put(amount,cc);
        return cc;
    }


    /**
     * dp 数组的迭代解法
     * @param coins
     * @param amount
     * @return
     */
    int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // 数组大小为 amount + 1，初始值也为 amount + 1
        Arrays.fill(dp, amount + 1);

        // base case
        dp[0] = 0;
        // 外层 for 循环在遍历所有状态的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 内层 for 循环在求所有选择的最小值
            for (int coin : coins) {
                // 子问题无解，跳过
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}
