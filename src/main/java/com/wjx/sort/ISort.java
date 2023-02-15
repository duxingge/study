package com.wjx.sort;

/**
 * O(n2)冒泡，插入排序,简单选择排序,希尔排序
 * O(nlogN)快排，归并排序,堆排序
 * O(n)桶排序
 * Q:为什么冒泡排序，插入排序，选择排序等是O(N2)复杂度？而快排和归并排序是O(nlogN)？
 * A：因为O(N2)级别的算法浪费了大量的比较结果，而O(nlogN)级别的算法是基于每次排序结果的
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
