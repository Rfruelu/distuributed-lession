package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.User;

/**
 * @author :lujia
 * @date :2018/7/24  17:57
 */
public class ClientDemo {

    public static void main(String[] args) {
        RemoteProxy remoteProxy=new RemoteProxy();

        UserService userService=remoteProxy.createClientProxy(UserService.class,"localhost",8080);

        User user = userService.getUser("1");

        System.out.println("id:"+user.getId()+"name:"+user.getName());
    }
}
