package com.wjx.dfs;

/**
 * @Author wangjiaxing
 * @Date 2022/5/12
 */
public class Dfs {
    public  int numIslands(char[][] grid) {
        if (grid==null||grid.length==0) {
            return 0;
        }
        byte[][] tags = new byte[grid.length][grid[0].length];
        int rows = grid.length;
        int cols = grid[0].length;
        int isLandNum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(tags[i][j]!=1) {
                    if(grid[i][j]=='1') {
                        tag(tags,grid,i,j);
                        isLandNum++;
                    }else {
                        continue;
                    }
                }else {
                    continue;
                }

            }
        }
        return isLandNum;
    }

    void tag(byte[][] tags,char[][] grid,int i,int j) {
        if(tags[i][j]==1) {
            return;
        }
        if(grid[i][j]=='1') {
            tags[i][j]=1;
            if(i+1<grid.length) {
                tag(tags,grid,i+1,j);
            }
            if (j+1<grid[0].length) {
                tag(tags,grid,i,j+1);
            }
            if(i>0) {
                tag(tags,grid,i-1,j);
            }
            if(j>0) {
                tag(tags,grid,i,j-1);
            }
        }

    }

}
