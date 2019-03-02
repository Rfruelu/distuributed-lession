package com.lujia.rpc;

import com.lujia.rpc.server.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author :lujia
 * @date :2018/7/24  15:06
 */
public class ClientDemo {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        HelloService helloService= (HelloService) Naming.lookup("rmi://10.108.151.135/helloService");
        helloService.sayHello("lujia");
    }
}
