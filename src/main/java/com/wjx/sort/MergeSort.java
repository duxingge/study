package com.wjx.sort;

/**
 * @Author wangjiaxing
 * @Date 2023/1/13
 */
public class MergeSort extends AbstractTimeCalculateSort{

    @Override
    public int[] sort(int[] nums) {
        return mergeSort(nums);
    }

    public int[] mergeSort(int[] nums) {
        if(nums.length==1) {
            return nums;
        }
        int mid = nums.length/2;
        int[] left = new int[mid];
        for (int i = 0; i < left.length; i++) {
            left[i] = nums[i];

        }
        mergeSort(left);
        int[] right = new int[nums.length-mid];
        for (int i = 0; i < right.length; i++) {
            right[i] = nums[mid+i];
        }
        mergeSort(right);
        int li=0,ri=0;
        for (int i = 0; i < nums.length; i++) {
            if(li>left.length-1 && ri>right.length-1) {
                return nums;
            }
            if(li> left.length-1 ) {
                nums[i] = right[ri];
                ri++;
            }else if(ri>right.length-1 ) {
                nums[i] = left[li];
                li++;
            }else if(left[li]>right[ri]) {
                nums[i] = right[ri];
                ri++;
            }else {
                nums[i] = left[li];
                li++;
            }
        }
        return nums;
    }


}
