package com.lujia.java8.lambda;

import java.util.stream.Stream;

/**
 * @author :lujia
 * @date :2018/7/26  16:41
 */
public class StreamDemo {

    public static void main(String[] args) {
        //初始化数据0-9集合
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                //过滤，判断值
                .filter(v -> v % 2 == 1)

                .map(f->f-1)
                //聚合
                .reduce(Integer::sum)
                //消费
                .ifPresent(System.out::println);
                //消费
                //.forEach(System.out::println);
    }
}
