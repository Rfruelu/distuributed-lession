package com.lujia.rpc.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author :lujia
 * @date :2018/7/24  14:47
 */
public class HelloserviceImpl extends UnicastRemoteObject implements HelloService {
    protected HelloserviceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sayHello(String arg) throws RemoteException{
        System.out.println("hello " + arg);
    }
}
