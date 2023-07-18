package com.wjx.hystrix;

import com.netflix.hystrix.HystrixThreadPool;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 *
 * {@link HystrixThreadPool }： 相同 key{@link HystrixThreadPoolKey }对应一个单例的HystrixThreadPool(通过{@link HystrixThreadPool.Factory} 单例模式实现)
 *  AbstractCommand ： command有属性HystrixThreadPoolKey对应一个HystrixThreadPool
 *
 * @Author wangjiaxing
 * @Date 2023/6/26
 */
public class KnHystrix {
    public static void main(String[] args) {
    }
}
