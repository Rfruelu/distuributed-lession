package com.lujia.java8.lambda;

/**
 * @author :lujia
 * @date :2018/7/26  11:32
 */
public class ThreadDemo {

    public static void main(String[] args) {

        printf("hello 1");
        Thread thread = new Thread(() -> {
            printf("hello 2");
        });

        thread.setName("thread--demo");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printf("hello 3");
    }


    public static void printf(String message){
        System.out.printf("[线程--%s],%s\n",Thread.currentThread().getName(),message);
    }
}
