package com.lujia.rpc.zk;

import com.lujia.rpc.zk.discovery.DiscoveryService;
import com.lujia.rpc.zk.discovery.DiscoveryServiceImpl;

/**
 * @author :lujia
 * @date :2018/7/24  17:57
 */
public class ClientDemo {

    public static void main(String[] args) throws InterruptedException {

        DiscoveryService discoveryService=new DiscoveryServiceImpl("192.168.128.128:2181");
        RemoteProxy remoteProxy=new RemoteProxy();

      ///  for (int i=0;i<9;i++){

            HelloService helloService = remoteProxy.createClientProxy(HelloService.class, discoveryService,"1.0");
            helloService.sayHello("123");
           // Thread.sleep(500);
       // }


    }
}
