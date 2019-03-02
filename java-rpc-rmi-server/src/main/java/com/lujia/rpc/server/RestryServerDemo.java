package com.lujia.rpc.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author :lujia
 * @date :2018/7/24  14:51
 */
public class RestryServerDemo {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        HelloService helloService=new HelloserviceImpl();
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://10.108.151.135/helloService",helloService);

    }
}
