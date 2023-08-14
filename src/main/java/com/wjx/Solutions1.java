package com.wjx;

/**
 * @Author wangjiaxing
 * @Date 2023/8/8
 */
public class Solutions1 {


    public int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < (nums.length - 1 - i); j++) {
                if(nums[j]>nums[j+1]) {
                    swap(nums,j,j+1);
                }
            }
        }
        return nums;
    }

    void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int tag = nums[i];
        nums[i] = nums[j];
        nums[j] = tag;
    }
}
