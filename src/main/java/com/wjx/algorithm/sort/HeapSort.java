package com.wjx.algorithm.sort;

/**
 * 
 * 须知1： i的左右节点2i,2i+1
 * @Author wangjiaxing
 * @Date 2023/1/13
 */
public class HeapSort extends AbstractTimeCalculateSort {

    @Override
    public int[] sort(int[] nums) {
        int lastNoLeaf = nums.length / 2 - 1;

        // 1.先整体调整为大顶堆
        for (int i = lastNoLeaf; i >= 0; i--) {
            judge(nums,nums.length,i);
        }
        // 2.每次取出堆顶元素(最大值),与数组目前最大有效值交换，之后有效值-1,重整0号节点大顶堆规则
        for (int i = 0; i < nums.length-1; i++) {
            swap(nums,0,nums.length-1-i);
            judge(nums,nums.length-1-i,0);
        }
        return nums;
    }


    /**
     * i节点左右子节点符合大顶堆规则时，调整i节点和其之下的树符合大顶堆规则
     *
     * @param nums     数组
     * @param i
     * @param validLen 数组有效长度
     * @return i节点被换到的位置
     */
    private void judge(int[] nums, int validLen, int i) {
        int swap = i;
        if (2 * i < validLen && nums[2 * i] > nums[swap]) {
            swap = 2 * i;
        }
        if (2 * i + 1 < validLen && nums[2 * i + 1] > nums[swap]) {
            swap = 2 * i + 1;
        }
        if (swap != i) {
            swap(nums, i, swap);
            judge(nums, validLen, swap);
        }
    }

}
