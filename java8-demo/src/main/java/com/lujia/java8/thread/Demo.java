package com.lujia.java8.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author :lujia
 * @date :2018/8/10  23:07
 */
public class Demo {
    private static int i=0;
    public static void main(String[] args) throws InterruptedException {

        Thread thread =new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().isInterrupted());
            while (!Thread.currentThread().isInterrupted()){
                i++;
           }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();

        System.out.println(thread.isInterrupted());
        System.out.println(i);
    }
}
