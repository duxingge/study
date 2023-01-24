package com.wjx.sort;

/**
 *
 * @Author wangjiaxing
 * @Date 2022/4/30
 */
public class QuickSort extends AbstractTimeCalculateSort {

    /**
     * 快速排序:本质是进行基准位tag归位([小于tag],tag,[大于等于tag])
     *
     * @return
     */
    public int[] quickSort(int[] nums, int start, int end) {
        if (end - start < 1) {
            return nums;
        }

        int tag = nums[start];
        int split = start;
        int i = start+1, j = end;
        while (j > i) {
            while (nums[j] >= tag && j > i) {
                j--;
            }
            while (nums[i] < tag && i < j) {
                i++;
            }
            if (j > i) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        // 此时索引顺序为tag<j<=i
        // 如果j==i，则只需比较tag与i;如果j<i，则j必然经过过i点，即num[i]>=tag，i不需要交换
        if(nums[j]<tag) {
            nums[start] = nums[j];
            nums[j] = tag;
            split = j;
        }
        quickSort(nums, start, split - 1);
        quickSort(nums, split + 1, end);
        return nums;
    }

    @Override
    public int[] sort(int[] nums) {
        return quickSort(nums, 0, nums.length - 1);
    }
}
