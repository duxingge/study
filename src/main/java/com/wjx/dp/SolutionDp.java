package com.wjx.dp;

/**
 * 动态规划的核心问题是穷举
 *
 * 既然是要求最值，核心问题是什么呢？求解动态规划的核心问题是穷举。因为要求最值，肯定要把所有可行的答案穷举出来，然后在其中找最值呗。
 *
 * 一般有两种方法：
 *      带备忘录的递归解法： 从大规模不断递归分解，并使用备忘录记录缓存，防止子问题重叠计算
 *      dp 数组的迭代（递推）解法 : 从小规模不断往上迭代
 *
 * # 1.自顶向下递归的动态规划  (带备忘录的递归解法) {@link Solution_money#coinChange1(int[], int)}
 * def dp(状态1, 状态2, ...):
 *     for 选择 in 所有可能的选择:
 *         # 此时的状态已经因为做了选择而改变
 *         result = 求最值(result, dp(状态1, 状态2, ...))
 *     return result
 *
 * # 2.自底向上迭代的动态规划 (dp 数组的迭代（递推）解法) {@link Solution_money#coinChange2(int[], int)}
 * # 初始化 base case
 * dp[0][0][...] = base case
 * # 进行状态转移
 *
 * for 状态1 in 状态1的所有取值：
 *     for 状态2 in 状态2的所有取值：
 *         for ...
 *             dp[状态1][状态2][...] = 求最值(选择1，选择2...)
 * @Author wangjiaxing
 * @Date 2023/3/1
 */
public class SolutionDp {


}
