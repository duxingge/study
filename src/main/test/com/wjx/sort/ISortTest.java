package com.wjx.sort;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @Author wangjiaxing
 * @Date 2023/1/3
 */
@RunWith(JUnit4.class)
public class ISortTest {

    @Test
    public void sort() {
        ArrayList<AbstractTimeCalculateSort> sorts = Lists.newArrayList(new QuickSort(), new BubbleSort(),
                new InsertionSort(),new ShellSort(), new SelectionSort(),new HeapSort(),new MergeSort());
//        ArrayList<AbstractTimeCalculateSort> sorts = Lists.newArrayList(new MergeSort());
        int[] nums = new int[10000];
        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(10000);
        }
        int[] bases = Arrays.copyOf(nums, nums.length);
        Arrays.sort(bases);
        for (AbstractTimeCalculateSort sort : sorts) {
            int[] target = Arrays.copyOf(nums, nums.length);
            sort.sortAndCalTimeSpend(target);
            assert Arrays.equals(bases, target);
        }
    }
}