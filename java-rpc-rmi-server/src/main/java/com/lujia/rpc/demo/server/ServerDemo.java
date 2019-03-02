package com.lujia.rpc.demo.server;

/**
 * @author :lujia
 * @date :2018/7/24  17:49
 */
public class ServerDemo {

    public static void main(String[] args) {

        RpcRegisty rpcRegisty=new RpcRegisty();


        UserService service=new UserServiceImpl();
        rpcRegisty.createReisty(8080,service);

    }
}
