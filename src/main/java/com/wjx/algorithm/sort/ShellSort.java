package com.wjx.algorithm.sort;

/**
 * 总体思想： 希尔变量规则 gap1 = length/2, gap(N+1) = gap(N)/2
 * 每次希尔变量的调整都使数组越来越有序,每次希尔变量的每组gap变量都采用插入排序。
 * 希尔排序时间复杂度为O(n2),好的gap选择可达到O(NlogN)
 *
 * @Author wangjiaxing
 * @Date 2023/1/12
 */
public class ShellSort extends AbstractTimeCalculateSort {

    @Override
    public int[] sort(int[] nums) {
        if (nums.length < 1) {
            return nums;
        }
        int gap = nums.length / 2;
        while (gap >= 1) {
            for (int i = 0; i < gap; i++) {
                insertionSort(nums, i, gap);
            }
            gap = gap / 2;
        }
        return nums;
    }

    /**
     * 间隔gap的队列进行插入排序。
     * 比如gap=3,start=0 则对num[0],num[3],num[6]...的数组进行排序
     *
     * @param nums
     * @param start
     * @param gap
     */
    void insertionSort(int[] nums, int start, int gap) {
        for (int j = start + gap; j < nums.length; j += gap) {
            for (int k = j; k >= gap; k -= gap) {
                if (nums[k] < nums[k - gap]) {
                    swap(nums, k, k - gap);
                }
            }
        }
    }


}
