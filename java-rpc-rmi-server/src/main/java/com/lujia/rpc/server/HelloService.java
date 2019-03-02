package com.lujia.rpc.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author :lujia
 * @date :2018/7/24  14:45
 */
public interface HelloService extends Remote{


    void sayHello(String arg)throws RemoteException;

}
