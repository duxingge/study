package com.wjx.algorithm.sort;

/**
 * 最好的情况下（基本有序）的插入排序时间复杂度O(n)，
 *
 * @Author wangjiaxing
 * @Date 2023/1/12
 */
public class InsertionSort extends AbstractTimeCalculateSort {

    @Override
    public int[] sort(int[] nums) {
        for (int i=1;i<nums.length;i++) {
            for (int j = i; j > 0; j--) {
                if(nums[j]<nums[j-1]) {
                    swap(nums,j,j-1);
                }
            }
        }
        return nums;
    }
}
