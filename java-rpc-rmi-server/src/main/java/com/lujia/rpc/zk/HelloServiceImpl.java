package com.lujia.rpc.zk;

import com.lujia.rpc.zk.annotation.RpcAnnotation;

/**
 * @author :lujia
 * @date :2018/8/6  22:01
 */
@RpcAnnotation(value =HelloService.class ,version = "1.0")
public class HelloServiceImpl implements HelloService {


    @Override
    public void sayHello(String message) {
        System.out.println("hello "+message);
    }
}
