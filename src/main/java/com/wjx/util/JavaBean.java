package com.wjx.util;

class JavaBean{
    private int value = 1;
    public String s = "abc";
    public final int f = 0x101;


    public void setValue(int v){
        final int temp = 4000;
        this.value = temp + v;
    }


    public int getValue(){
        return value;
    }
}
