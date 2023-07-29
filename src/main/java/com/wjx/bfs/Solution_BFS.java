package com.wjx.bfs;

import com.google.common.collect.Lists;

import java.util.*;

/**
 *
 * BFS 的核心思想应该不难理解的，就是把一些问题抽象成图，从一个点开始，向四周开始扩散。一般来说，我们写 BFS 算法都是用「队列」这种数据结构，每次将一个节点周围的所有节点加入队列。
 *
 * BFS 问题的本质就是让你在一幅「图」中找到从起点 start 到终点 target 的**最近距离**
 *
 * 这个广义的描述可以有各种变体，比如走迷宫，有的格子是围墙不能走，从起点到终点的最短距离是多少？如果这个迷宫带「传送门」可以瞬间传送呢？
 *
 * 再比如说两个单词，要求你通过某些替换，把其中一个变成另一个，每次只能替换一个字符，最少要替换几次？
 *
 * 再比如说连连看游戏，两个方块消除的条件不仅仅是图案相同，还得保证两个方块之间的最短连线不能多于两个拐点。你玩连连看，点击两个坐标，游戏是如何判断它俩的最短连线有几个拐点的？
 *
 * 再比如……
 *
 * 净整些花里胡哨的，本质上看这些问题都没啥区别，就是一幅「图」，让你从一个起点，走到终点，问最短路径。这就是 BFS 的本质，框架搞清楚了直接默写就好。
 *
 * @Author wangjiaxing
 * @Date 2023/7/28
 */
public class Solution_BFS {

//    // BFS解题套路框架
//    // 计算从起点 start 到终点 target 的最近距离
//    int BFS(Node start, Node target) {
//        Queue<Node> q; // 核心数据结构
//        Set<Node> visited; // 避免走回头路
//
//        q.offer(start); // 将起点加入队列
//        visited.add(start);
//
//        while (!q.isEmpty()) {
//            int sz = q.size();
//            /* 将当前队列中的所有节点向四周扩散 */
//            for (int i = 0; i < sz; i++) {
//                Node cur = q.poll();
//                /* 划重点：这里判断是否到达终点 */
//                if (cur is target)
//                return step;
//                /* 将 cur 的相邻节点加入队列 */
//                for (Node x : cur.aroundNodes()) {
//                    if (visited.contains(x)) {
//                        q.offer(x);
//                        visited.add(x);
//                    }
//                }
//            }
//        }
//        // 如果走到这里，说明在图中没有找到目标节点
//    }



    class Node {
        List<Node> aroundNodes() {
            //返回扩展的所有节点
            return new ArrayList<>();
        }
    }


}
