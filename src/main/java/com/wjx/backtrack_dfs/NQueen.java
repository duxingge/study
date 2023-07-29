package com.wjx.backtrack_dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 *
 * n皇后问题 研究的是如何将 n个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 * 给你一个整数 n ，返回所有不同的n皇后问题 的解决方案。
 *
 * 每一种解法包含一个不同的n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 输入：n = 4
 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/n-queens
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author wangjiaxing
 * @Date 2023/4/28
 */
public class NQueen {

    class Solution {
        List<List<String>> res = new ArrayList<>();

        public List<List<String>> solveNQueens(int n) {
            char[][] init = new char[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(init[i],'.');
            }
            backtrack(init,0);
            return res;
        }

        void backtrack(char[][] board, int row) {
            if (row == board.length) {
                res.add(gen(board));
                return;
            }
            for (int y = 0; y < board.length; y++) {
                if(!canChoose(row,y,board)) {
                    continue;
                }
                board[row][y] = 'Q';
                backtrack(board,row+1);
                board[row][y] = '.';
            }
        }

        private boolean canChoose(int x, int y, char[][] chars) {

            if(chars[x][y]=='Q') {
                return false;
            }
            final int length = chars.length;
            for (int i = 0; i < chars.length; i++) {
                if(chars[x][i]=='Q'||chars[i][y]=='Q') {
                    return false;
                }
                int xMin = x-i;
                int yMin = y-i;
                int xMax = x+i;
                int yMax = y+i;
                if (xMin >=0 && yMin >=0 && chars[xMin][yMin] == 'Q') {
                    return false;
                }
                if (xMin >=0 && yMax < length && chars[xMin][yMax] == 'Q') {
                    return false;
                }
                if (xMax <length && yMax < length && chars[xMax][yMax] == 'Q') {
                    return false;
                }
                if (xMax <length && yMin >= 0 && chars[xMax][yMin] == 'Q') {
                    return false;
                }
            }
            return true;
        }


        private List<String> gen(char[][] chars) {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < chars.length; i++) {
                StringBuilder r = new StringBuilder();
                for (int j = 0; j < chars[i].length; j++) {
                    r.append(chars[i][j]);
                }
                res.add(r.toString());
            }
            return res;
        }

    }

}
