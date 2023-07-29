package com.wjx.java.concurrency.bookTemplate.unit1;

import com.wjx.java.annotions.ThreadUnSafe;
import com.wjx.java.concurrency.bookTemplate.unit1.kit.Event;
import com.wjx.java.concurrency.bookTemplate.unit1.kit.EventListener;
import com.wjx.java.concurrency.bookTemplate.unit1.kit.EventSource;
import com.wjx.java.concurrency.bookTemplate.unit1.kit.Thread1;

/**
 * 前置知识：
 *      1. 匿名内部类对象含有对外部对象的引用 {@link ThisEscape#ThisEscape(EventSource)}
 * -------------------
 * 逸出：在对象还未准备好之前将它发布
 * 1. 将内部可变的数据对象发布，导致逸出 {@link ThisEscape#getStates()}
 * 2.构造器中this逸出举例：
 *      2.1 在构造器中创建了匿名内部类，并将内部类发布 {@link ThisEscape#ThisEscape(EventSource)}
 *      2.2 匿名内部类实例包含了对封装对象的引用
 * @Author wangjiaxing
 * @Date 2023/2/16
 */
public class ThisEscape {
    //所有的州
    private String[] states = new String[]{"AK","AL"};

    /**
     * 在这个构造器中，通过外部对象source发布了构造器中的匿名内部类，导致this(即ThisEscape实例)逸出
     * @param source
     */
    @ThreadUnSafe
    public ThisEscape(EventSource source) {
        source.setEventListener(new EventListener() {
            @Override
            public void onEvent(Event event) {
                //Q1:匿名内部类对象为什么能访问states？A: 匿名内部类对象含有对外部对象(即ThisEscape对象)的引用
                System.out.println(states);
            }
        });
    }

    /**
     * 构造器中: this被新线程显式/隐式的共享 + 线程启动 = 构造器this逸出
     *-----------------
     * 下面代码，ThisEscape还没有实例化完成，但是线程已经启动，即新线程可以访问尚未准备好的ThisEscape实例，从而导致this逸出。
     *
     * 解决方案是：线程的启动放到构造器之外，提供一个start()方法去启动线程
     */
    @ThreadUnSafe
    public ThisEscape() {
        //情景1： this被新线程隐式的共享
        new Thread(new Runnable(){
            @Override
            public void run() {
               //由于该Runnable是ThisEscape的匿名内部类，所以包含对外部对象的引用，所以可以访问states.
                System.out.println(states);
            }
        }).start();

        //情景二：this被新线程显示的共享,
        new Thread1(this).start();
    }

    /**
     * variableEscape
     * 该方法发布了本来私有的states,导致任何一个调用者都可以修改status
     * @return
     */
    @ThreadUnSafe
    public String[] getStates() {
        return states;
    }


}
