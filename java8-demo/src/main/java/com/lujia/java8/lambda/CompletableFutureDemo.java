package com.lujia.java8.lambda;

import java.util.concurrent.CompletableFuture;

/**
 * @author :lujia
 * @date :2018/7/26  17:03
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        printf("start main thread");

        CompletableFuture.supplyAsync(()-> {
            printf("step one return \"hello\"");
            return "hello";
        }).thenApplyAsync(result->{
            printf("step two return step one result + word ");
            return result+" word";
        }).thenAccept(CompletableFutureDemo::printf)
                .whenComplete((v,e)->{
                    printf("Performs over ");
                })
        .join()
        ;

    }

    public static void printf(String message){
        System.out.printf("[线程-%s]-%s\n",Thread.currentThread().getName(),message);
    }
}
