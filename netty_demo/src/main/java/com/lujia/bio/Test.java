package com.lujia.bio;

import java.util.Random;

/**
 * @author :lujia
 * @date :2018/11/1  10:37
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {


        new Thread(() -> {

            while (true){
                Server.start();
            }
        },"T-1").start();


        Thread.sleep(600);

        Random random =new Random();

        String [] arr={"a","b","c"};
        new Thread(()->{

            while (true){
                String request=arr[random.nextInt(3)];
                Client.send(request);
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"T-2").start();
    }
}
