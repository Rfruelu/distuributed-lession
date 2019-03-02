package com.lujia.rpc.demo.server;

import java.lang.reflect.Proxy;

/**
 * @author :lujia
 * @date :2018/7/24  17:59
 */
public class RemoteProxy {


    public <T> T createClientProxy(final  Class<?> tClass, final String localhost, final int port) {

        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),new Class[]{tClass},new RemoteInvocationHandler(localhost,port));

    }
}
