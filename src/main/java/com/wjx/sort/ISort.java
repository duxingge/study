package com.wjx.sort;

/**
 * O(n2)冒泡，插入排序,简单选择排序,希尔排序
 * O(nlogN)快排，归并排序,堆排序
 * O(n)桶排序
 *
 * @Author wangjiaxing
 * @Date 2023/1/3
 */
public interface ISort {

    int[] sort(int[] nums);

    default String sortName() {
        return this.getClass().getSimpleName();
    }

    default void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int tag = nums[i];
        nums[i] = nums[j];
        nums[j] = tag;
    }


}
