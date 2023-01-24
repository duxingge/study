package com.wjx.sort;

/**
 * 算法：每次选中一个最小的数和最前列交换
 * 优化点： 每次选中最小数和最大数分别和最前/最后的数交换
 *
 * @Author wangjiaxing
 * @Date 2023/1/13
 */
public class SelectionSort extends AbstractTimeCalculateSort {


    @Override
    public int[] sort(int[] nums) {
        return sortOptimization(nums);
    }

    /**
     * 每次选中一个最小的数和最前列交换
     *
     * @param nums
     * @return
     */
    public int[] oldSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            swap(nums, min, i);
        }
        return nums;
    }

    /**
     * 每次选中最小数和最大数分别和最前/最后的数交换
     */
    public int[] sortOptimization(int[] nums) {
        for (int i = 0; i < (nums.length / 2); i++) {
            int min = i, max = i;
            for (int j = i; j < nums.length - i; j++) {
                if (nums[j] > nums[max]) {
                    max = j;
                }
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            if(i!=max) {
                swap(nums, i, min);
                swap(nums, nums.length - 1 - i, max);
            }else {
                swap(nums, i, min);
                swap(nums, nums.length - 1 - i, min);
            }

        }
        return nums;
    }
}
