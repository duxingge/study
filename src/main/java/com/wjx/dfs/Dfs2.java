package com.wjx.dfs;

/**
 * 给你一个大小为 m x n 的二进制矩阵 grid 。
 *
 * 岛屿是由一些相邻的1(代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设grid 的四个边缘都被 0（代表水）包围着。
 *
 * 岛屿的面积是岛上值为 1 的单元格的数目。
 *
 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/max-area-of-island
 *
 * @Author wangjiaxing
 * @Date 2022/6/1
 */
public class Dfs2 {

    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                } else {
                    int area = maxAreaWithoutTag(grid, i, j);
                    if (area > max) {
                        max = area;
                    }
                }
            }
        }
        return max;
    }

    /**
     * 拥有i,j的最大岛屿路径 =
     *      i，j=0, 则结果为0
     *      i,j=1,  则结果= 1+ 将i,j设为0后的 Max{包含上/下/左/右}岛屿
     * 注意：每过一个i,j节点，就将节点值设为0,表示该节点相关的岛屿已遍历过了。
     * @param grid
     * @param i
     * @param j
     * @return
     */
    int maxAreaWithoutTag(int[][] grid, int i, int j) {
        if (grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        if (i > 0) {
            up = maxAreaWithoutTag(grid, i - 1, j);
        }
        if (j > 0) {
            left = maxAreaWithoutTag(grid, i, j - 1);
        }
        if (i < grid.length - 1) {
            down = maxAreaWithoutTag(grid, i + 1, j);
        }
        if (j < grid[0].length - 1) {
            right = maxAreaWithoutTag(grid, i, j + 1);
        }
        return 1 + up + down + left + right;

    }
}
