package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author :lujia
 * @date :2018/7/24  18:06
 */
public class RemoteInvocationHandler implements InvocationHandler{

    private String url;
    private int port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RemoteInvocationHandler(String url,int port) {
        this.url=url;
        this.port=port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request=new RpcRequest();
        request.setMethodName(method.getName());
        request.setParameters(args);

        TcpTransport tcpTransport=new TcpTransport(url,port);
        return tcpTransport.send(request);
    }
}
