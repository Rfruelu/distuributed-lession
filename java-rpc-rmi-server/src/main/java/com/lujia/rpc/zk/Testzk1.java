package com.lujia.rpc.zk;

/**
 * @author :lujia
 * @date :2018/8/6  23:08
 */
public class Testzk1 {

    public static void main(String[] args) {
        ZkRegistry zkRegistry=new ZkRegistryImpl("192.168.128.128:2181");

        RpcServer server=new RpcServer(zkRegistry,"127.0.0.1:8082");

        HelloService helloService=new HelloServiceImpl();

        server.bind(helloService);

        server.publisher();


    }
}
