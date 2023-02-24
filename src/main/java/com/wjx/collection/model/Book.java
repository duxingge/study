package com.wjx.collection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wangjiaxing
 * @Date 2023/2/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private int price;
}
