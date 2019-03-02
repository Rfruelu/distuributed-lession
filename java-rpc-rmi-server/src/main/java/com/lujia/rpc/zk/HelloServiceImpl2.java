package com.lujia.rpc.zk;

import com.lujia.rpc.zk.annotation.RpcAnnotation;

/**
 * @author :lujia
 * @date :2018/8/6  22:01
 */
@RpcAnnotation(value =HelloService.class ,version = "2.0")
public class HelloServiceImpl2 implements HelloService {


    @Override
    public void sayHello(String message) {
        System.out.println("hello 2.0"+message);
    }
}
