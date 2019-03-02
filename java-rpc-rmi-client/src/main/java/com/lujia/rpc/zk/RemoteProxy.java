package com.lujia.rpc.zk;

import com.lujia.rpc.zk.discovery.DiscoveryService;

import java.lang.reflect.Proxy;

/**
 * @author :lujia
 * @date :2018/7/24  17:59
 */
public class RemoteProxy {




    public <T> T createClientProxy(final  Class<?> tClass, DiscoveryService discoveryService,String version) {

        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),new Class[]{tClass},new RemoteInvocationHandler(discoveryService,version));

    }
}
