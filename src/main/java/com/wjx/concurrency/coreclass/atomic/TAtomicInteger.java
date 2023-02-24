package com.wjx.concurrency.coreclass.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * 1。内部是通过CAS+volatile来保证线程安全.CAS保证原子性操作，如AtomicInteger 内部有value字段使用volatile保证可见性。
 * 2。CAS具有ABA问题需要注意
 *
 * 3.1 基本类型原子类：
 * {@link java.util.concurrent.atomic.AtomicInteger}
 * {@link java.util.concurrent.atomic.AtomicLong}
 * {@link java.util.concurrent.atomic.AtomicBoolean}
 *
 * 3.2 数组型原子类：用法基本类似基本类型原子类
 * {@link java.util.concurrent.atomic.AtomicIntegerArray}
 * {@link java.util.concurrent.atomic.AtomicLongArray}
 * {@link java.util.concurrent.atomic.AtomicReferenceArray}
 *
 * 3.3 引用类型原子类： 可以将引用类V的一个属性设为版本号，从而解决ABA问题
 * {@link java.util.concurrent.atomic.AtomicReference} AtomicReference<V>
 *
 * 3.4 对象属性修改原子类:
 *      第一步，因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法 newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
 *      第二步，更新的对象属性必须使用 public volatile 修饰符。
 * {@link java.util.concurrent.atomic.AtomicIntegerFieldUpdater}:原子更新整形字段的更新器
 * {@link java.util.concurrent.atomic.AtomicLongFieldUpdater}：原子更新长整形字段的更新器
 * {@link java.util.concurrent.atomic.AtomicReferenceFieldUpdater} ：原子更新引用类型里的字段的更新器
 *
 * @Author wangjiaxing
 * @Date 2023/1/31
 */
public class TAtomicInteger {

    public static void main(String[] args) {
        //基础类测试
//        TestAtomicInteger threadSafeInt = new TestAtomicInteger();
//        TestSynchronize threadSafeInt = new TestSynchronize();
//        multiThreadRun(threadSafeInt,it->it.getAndIncrease());


        //数组测试
//        int[] arrs = new int[]{0, 0, 0};
//        AtomicIntegerArray numArray = new AtomicIntegerArray(arrs);
//        multiThreadRun(numArray,it->it.getAndIncrement(0));

        //引用类测试
//        testAtomicReference();


        //修改属性测试
        testAtomicIntegerFieldUpdater();
    }



    static <T> void multiThreadRun(T obj, Function<T,Integer> getFunc) {
        List<CompletableFuture<Integer>> allCompletableFuture = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            allCompletableFuture.add(CompletableFuture.supplyAsync(() -> getFunc.apply(obj)));
        }

        int sum = allCompletableFuture.stream().map(it -> {
            try {
                return it.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }).mapToInt(a -> (Integer) a).sum();
        System.out.println("result: " + sum);
    }

    /**
     * 实现一，多线程下使用原子类保证线程安全
     * AtomicInteger
     * AtomicLong
     * AtomicBoolean
     */
    //正确结果: 124750

    static class TestAtomicInteger {
        AtomicInteger num = new AtomicInteger(0);
        int getAndIncrease() {
            return num.getAndIncrement();
        }
    }

    /**
     * 实现二，多线程下使用原子类保证线程安全
     */
    static class TestSynchronize {
        static int num = 0;

        int getAndIncrease() {
            synchronized (this) {
                num++;
                return num-1;
            }
        }
    }


    static void testAtomicReference() {
        AtomicReference<Person> ar = new AtomicReference<>();
        Person person = new Person("SnailClimb", 22);
        ar.set(person);
        Person updatePerson = new Person("Daisy", 20);
        ar.compareAndSet(person, updatePerson);

        System.out.println(ar.get().getName());
        System.out.println(ar.get().getAge());
    }

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

    static void testAtomicIntegerFieldUpdater() {
        AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
        User user = new User("zhangsan",24);
        multiThreadRun(user,it->fieldUpdater.getAndIncrement(it));
    }

    static class User {
        private String name;
        public volatile int age;

        public User(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }
}


