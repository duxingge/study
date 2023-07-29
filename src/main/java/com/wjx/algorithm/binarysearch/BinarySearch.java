package com.wjx.algorithm.binarysearch;

/**
 *
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 如果数组中不存在目标值 target，返回[-1, -1]。
 *
 * 进阶：
 *
 * 你可以设计并实现时间复杂度为O(log n)的算法解决此问题吗？
 * 
 *
 * 示例 1：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例2：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 *
 * 示例 3：
 *
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 * @Author wangjiaxing
 * @Date 2022/4/30
 */
public class BinarySearch {
    //先找>=target的第一个
    //再找>target的第一个
    public int[] searchRange(int[] nums, int target) {
        int l=search(nums,target);
        int r=search(nums,target+1);
        if(l==nums.length||nums[l]!=target)
            return new int[]{-1,-1};
        return new int[]{l,r-1};
    }
    //找>=target的第一个
    public int search(int[] nums,int target){
        int l=0,r=nums.length;
        while(l<r){
            int mid=(r+l)>>1;
            if(nums[mid]>=target)
                r=mid;
            else
                l=mid+1;
        }
        return l;
    }
}
