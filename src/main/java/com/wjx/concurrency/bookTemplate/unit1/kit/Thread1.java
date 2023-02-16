package com.wjx.concurrency.bookTemplate.unit1.kit;

import com.wjx.concurrency.bookTemplate.unit1.ThisEscape;

/**
 * @Author wangjiaxing
 * @Date 2023/2/16
 */
public class Thread1 extends Thread{
    private ThisEscape escape;

    public Thread1(ThisEscape escape) {
        this.escape = escape;
    }

    @Override
    public void run() {
        System.out.println(escape.getStates());
    }
}
