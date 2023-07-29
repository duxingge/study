package com.wjx.java.jvm;

/**
 *
 *
 *
 *
 * @Author wangjiaxing
 * @Date 2023/7/26
 */
public class Kn_String {

    public static void main(String[] args) {
        //unit1
        String str1 = "str";
        String str2 = "ing";
        /**
         * 对于编译期可以确定值的字符串，也就是常量字符串 ，jvm 会将其存入字符串常量池。
         * 并且，字符串常量拼接得到的字符串常量在编译阶段就已经被存放字符串常量池，这个得益于编译器的优化。
         */
        String str3 = "str" + "ing";
        /**
         * 1。 引用的值在程序编译期是无法确定的，编译器无法对其进行优化。
         * 引用类型+ 实际上会在运行阶段,调用stringBuilder的append，然后创建一个新的String对象
         * 2。 不过，字符串使用 final 关键字声明之后，可以让编译器当做常量来处理。
         */
        String str4 = str1 + str2;
        String str5 = "string";

        final String str6 = "str";
        final String str7 = "ing";
        String str8 = str6 + str7;
        System.out.println(str3 == str4);//false
        System.out.println(str3 == str5);//true
        System.out.println(str4 == str5);//false
        System.out.println(str5 == str8);//true

        /**
         * unit2,  String s = new String("string");创建了几个对象？
         * A: 1个或者2个。
         * 1） 首先在堆中创建一个String对象A。
         * 2） 使用"string"的字符串常量池引用对对象A赋值，
         * LDC: 字符串"string"存在于堆中的运行时全局字符串常量池中，有引用直接返回，没有则在堆中创建并返回引用
         */

        String s = new String("string");

    }
}
