package com.wjx.algorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * //记录leeCode一些常用的加速工具代码
 * @Author wangjiaxing
 * @Date 2023/8/9
 */
public class AlgorithmBasic {

    public static void main(String[] args) {
        //------------string 相关 ------------
        //1. 字符串反转
        new StringBuilder("1234").reverse().toString();

        //字符转整数
        System.out.println('9' - '0');


        //----------------- 数组 -----------------
        int[] intArrs = new int[2];
        //1. 数字填充
        Arrays.fill(intArrs,1);
        //2. 数字排序
        Arrays.sort(intArrs);

        //----------------- 队列 -----------------
        // 队列或者栈
        LinkedList<Integer> dq = new LinkedList<>();

        // 小顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(10,(a,b)->a-b);


    }
}
