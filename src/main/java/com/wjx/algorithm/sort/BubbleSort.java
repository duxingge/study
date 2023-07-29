package com.wjx.algorithm.sort;

/**
 * @Author wangjiaxing
 * @Date 2023/1/11
 */
public class BubbleSort extends AbstractTimeCalculateSort {


    @Override
    public int[] sort(int[] nums) {
        return bubbleSort1(nums);
    }


    public int[] bubbleSort1(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < (nums.length - 1 - i); j++) {
                if(nums[j]>nums[j+1]) {
                    swap(nums,j,j+1);
                }
            }
        }
        return nums;
    }
}
