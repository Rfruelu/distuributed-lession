package com.lujia.soap.dubbo.serviceImpl;

import com.lujia.soap.dubbo.service.HelloService;

/**
 * @author :lujia
 * @date :2018/8/9  21:09
 */
public class MyHelloServiceImpl implements HelloService{
    @Override
    public void say(String message) {
//        System.out.println("Hello "+message);
        System.out.printf("Hello %s\n",message);
    }
}
