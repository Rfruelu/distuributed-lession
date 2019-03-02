package com.lujia.rpc.zk;

import com.lujia.rpc.demo.domain.RpcRequest;
import com.lujia.rpc.zk.discovery.DiscoveryService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author :lujia
 * @date :2018/7/24  18:06
 */
public class RemoteInvocationHandler implements InvocationHandler{

   private DiscoveryService discoveryService;

   private String version;
    public RemoteInvocationHandler(DiscoveryService discoveryService,String version) {
        this.discoveryService = discoveryService;
        this.version=version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request=new RpcRequest();
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(version);
        String name = method.getDeclaringClass().getName();
        request.setClassName(name);
        TcpTransport tcpTransport=new TcpTransport(discoveryService.discovery(name,version));
        return tcpTransport.send(request);
    }
}
