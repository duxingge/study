package com.wjx.algorithm.sort;

/**
 *
 * @Author wangjiaxing
 * @Date 2022/4/30
 */
public class QuickSort extends AbstractTimeCalculateSort {

    @Override
    public int[] sort(int[] nums) {
        return quickSortRandom(nums, 0, nums.length - 1);
    }

    /**
     * 快速排序:本质是进行基准位tag归位([小于tag],tag,[大于等于tag])
     * 版本一: 对于某些特殊情况如：1，2，3，4，5，6，7..的时间复杂度为O(n2)
     * 版本二: 每次tag位置随机选, tag=random(start,end), 然后tag与start交换，再执行版本一
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
        quickSortRandom(nums, start, split - 1);
        quickSortRandom(nums, split + 1, end);
        return nums;
    }


    public int[] quickSortRandom(int[] nums, int start, int end) {
        //保证每次的选值随机，从而不会遇到最坏情况
        int random = start+ (int) Math.random()*(end-start);
        swap(nums,start,random);
        return quickSort(nums,start,end);
    }

}
