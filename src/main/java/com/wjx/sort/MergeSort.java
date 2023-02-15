package com.wjx.sort;

import com.google.common.collect.Lists;

/**
 * 归并排序的本质是，每次merge后，子队列有序，这种有序性为下次递归打下了好的基础。
 *
 * @Author wangjiaxing
 * @Date 2023/1/13
 */
public class MergeSort extends AbstractTimeCalculateSort {

    @Override
    public int[] sort(int[] nums) {
        process(nums, 0, nums.length - 1);
        return nums;
    }

    public void process(int[] nums, int start, int end) {
        if (end <= start) {
            return;
        }
        int mid = start + ((end - start) >> 1);
        process(nums, start, mid);
        process(nums, mid + 1, end);
        merge(nums, start, mid, end);
    }

    /**
     * 每次merge后，[start,mid],[mid+1,end]两个队列都变得有序了
     *
     * @param nums
     * @param start
     * @param mid
     * @param end
     */
    private void merge(int[] nums, int start, int mid, int end) {
        int[] newArr = new int[end - start + 1];
        int l = start, r = mid + 1, newIndex = 0;
        while (l < mid + 1 && r < end + 1) {
            newArr[newIndex++] = nums[l] < nums[r] ? nums[l++] : nums[r++];
        }
        while (l < mid + 1) {
            newArr[newIndex++] = nums[l++];
        }
        while (r < end + 1) {
            newArr[newIndex++] = nums[r++];
        }
        for (int i = 0; i < newArr.length; i++) {
            nums[start + i] = newArr[i];
        }
    }


    //例子1。求小和问题：给出一串数，所有数左边比它小的数的和的和
    //如：1，3，4，2，5。 数字3有1,数字4有1，3，数字2有1,数字5有1,3,4,2,则小和为1+1+3+1+1+3+4+2
    static int template1_smallSum(int[] nums) {
        //分析：可以转换为求Sum(每个数的右边比它大的所有数的和).采用归并算法
        return template1_process(nums, 0, nums.length - 1);
    }

    private static int template1_process(int[] nums, int start, int end) {
        if(end<=start) {
            return 0;
        }

        int mid = start + ((end - start) >> 1);
        return template1_process(nums,start,mid) +
                template1_process(nums,mid+1,end)+
                template1_merge(nums,start,mid,end);
    }

    /**
     * 1.每次排序时，[mid,end]已经有序。这样保证计算 大于 比较值的次数时复杂度为O(1)
     * 2.左数组和右数组值相同时，先放右数组的值。因为要统计大于左值的所有右数组数。即 11122 1133 合并数组11 ->11122 133 合并数组 111
     * @param nums
     * @param start
     * @param mid
     * @param end
     * @return
     */
    private static int template1_merge(int[] nums, int start, int mid, int end) {
        int[] newArr = new int[end - start + 1];
        int l = start, r = mid + 1, newIndex = 0,result=0;
        while (l < mid + 1 && r < end + 1) {
            if (nums[l] < nums[r]) {
                result+=(end-r+1)*nums[l];
                newArr[newIndex++] =  nums[l++];

            }else {
                newArr[newIndex++] =  nums[r++];
            }
        }
        while (l < mid + 1) {
            newArr[newIndex++] = nums[l++];
        }
        while (r < end + 1) {
            newArr[newIndex++] = nums[r++];
        }
        for (int i = 0; i < newArr.length; i++) {
            nums[start + i] = newArr[i];
        }
        return result;
    }


    public static void main(String[] args) {
        int[] template = new int[]{1,3,4,2,5};
        System.out.println(template1_smallSum(template));
    }
}
