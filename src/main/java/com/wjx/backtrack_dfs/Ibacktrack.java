package com.wjx.backtrack_dfs;

/**
 * 解决一个回溯问题，实际上就是一个决策树的遍历过程，站在回溯树的一个节点上，你只需要思考 3 个问题：
 *
 * 1、路径：也就是已经做出的选择。
 *
 * 2、选择列表：也就是你当前可以做的选择。
 *
 * 3、结束条件：也就是到达决策树底层，无法再做选择的条件。
 * example {@link FullPermutation}
 * @Author wangjiaxing
 * @Date 2023/4/5
 */
public interface Ibacktrack {

//    default void backtrackMode(选择节点1，选择列表1) {
//        if(满足结束条件) {
//            result.add(xx);
//            return;
//        }
//        selectOneA();
//        for (选择节点2 : 选择列表1 ) {
//            backtrackMode(选择节点2, 选择列表2);
//        }
//        backA();
//    }
}
