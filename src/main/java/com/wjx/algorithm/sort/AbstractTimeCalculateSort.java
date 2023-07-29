package com.wjx.algorithm.sort;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @Author wangjiaxing
 * @Date 2023/1/13
 */
public abstract class AbstractTimeCalculateSort implements ISort{

    int[] sortAndCalTimeSpend(int[] nums) {
        StopWatch stopWatch = StopWatch.createStarted();
        int[] sortNums = sort(nums);
        System.out.println(this.getClass().getName() + " cost time : "+ stopWatch);
        return sortNums;

    }
}
