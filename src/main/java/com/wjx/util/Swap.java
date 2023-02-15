package com.wjx.util;

/**
 * @Author wangjiaxing
 * @Date 2023/1/26
 */
public class Swap {
    public static void main(String[] args) {
        int a= 1;
        int b= 1;
        a = a ^ b;
        b = b ^ a;
        a = b ^ a;
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }

    static void swap(int a,int b) {
        a = a ^ b;
        b = b ^ a;
        a = b ^ a;
    }
}
