package com.lujia.java8.lambda;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

/**
 * @author :lujia
 * @date :2018/7/27  17:21
 */
public class SpringEventDemo {

    public static void main(String[] args) {

        //多播监听事件
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster=new SimpleApplicationEventMulticaster();


        simpleApplicationEventMulticaster.addApplicationListener(listener->{
            System.out.printf("[currentThread name is %s]--%s\n",Thread.currentThread().getName(),listener);
        });
        simpleApplicationEventMulticaster.multicastEvent(new PayloadApplicationEvent("helo","hello word"));

    }
}
