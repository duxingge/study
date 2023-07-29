package com.wjx.algorithm.twopoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 *  双指针技巧:
 *      1.双指针解决链表时可以考虑结合 虚拟头节点 技巧使用
 * @Author wangjiaxing
 * @Date 2023/2/1
 */

@Retention(RetentionPolicy.SOURCE)
@Target(value={METHOD, TYPE})
public @interface TwoPoint {
}
