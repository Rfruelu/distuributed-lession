package com.lujia.soap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author :lujia
 * @date :2018/8/9  23:24
 */
public class Bootstrap1 {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext("META-INF/spring/dubbo-cluster1.xml");
        context.start();
        System.in.read();
    }
}
